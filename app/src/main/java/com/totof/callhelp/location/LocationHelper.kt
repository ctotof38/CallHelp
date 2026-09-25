package com.totof.callhelp.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.totof.callhelp.util.DfciConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class LocationState(
    val isPermissionGranted: Boolean = false,
    val isLocationEnabled: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val dfci: String? = null,
    val lastUpdated: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

class LocationHelper(private val context: Context) {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var onStateChanged: ((LocationState) -> Unit)? = null

    private var currentState = LocationState()

    fun checkPermissionsAndLocation(): LocationState {
        val finePerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        val coarsePerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        val hasPerm = finePerm || coarsePerm
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val newState = currentState.copy(
            isPermissionGranted = hasPerm,
            isLocationEnabled = gpsEnabled,
        )
        currentState = newState
        return newState
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(onUpdate: (LocationState) -> Unit) {
        this.onStateChanged = onUpdate

        val state = checkPermissionsAndLocation()
        if (!state.isPermissionGranted) {
            val updated = state.copy(
                isLoading = false,
                error = "Permission de géolocalisation non accordée",
            )
            currentState = updated
            onUpdate(updated)
            return
        }

        if (!state.isLocationEnabled) {
            val updated = state.copy(
                isLoading = false,
                error = "Géolocalisation désactivée dans les paramètres de l'appareil",
            )
            currentState = updated
            onUpdate(updated)
            return
        }

        val loadingState = state.copy(isLoading = true, error = null)
        currentState = loadingState
        onUpdate(loadingState)

        // Try last known location first for immediate feedback
        var lastLoc: Location? = null
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        if ((lastLoc == null) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        lastLoc?.let { processNewLocation(it) }

        // Request active updates
        try {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    1f,
                    locationListener,
                )
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    1f,
                    locationListener,
                )
            }
        } catch (e: Exception) {
            val errState = currentState.copy(isLoading = false, error = e.localizedMessage)
            currentState = errState
            onUpdate(errState)
        }
    }

    fun stopLocationUpdates() {
        try {
            locationManager.removeUpdates(locationListener)
        } catch (_: Exception) {}
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            processNewLocation(location)
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {
            checkPermissionsAndLocation()
        }
        override fun onProviderDisabled(provider: String) {
            checkPermissionsAndLocation()
        }
    }

    private fun processNewLocation(location: Location) {
        val lat = location.latitude
        val lng = location.longitude
        val dfciStr = DfciConverter.fromWgs84(lat, lng)
        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRENCH).format(Date(location.time))

        val updated = currentState.copy(
            latitude = lat,
            longitude = lng,
            dfci = dfciStr,
            lastUpdated = timestamp,
            isLoading = false,
            error = null,
        )
        currentState = updated
        onStateChanged?.invoke(updated)
    }

    fun openLocationSettingsIntent(): Intent {
        return Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }
}
