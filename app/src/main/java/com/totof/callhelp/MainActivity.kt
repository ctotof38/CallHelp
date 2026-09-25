package com.totof.callhelp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.totof.callhelp.location.LocationHelper
import com.totof.callhelp.location.LocationState
import com.totof.callhelp.ui.screens.FireReportScreen
import com.totof.callhelp.ui.screens.HomeScreen
import com.totof.callhelp.ui.screens.PersonHelpReportScreen
import com.totof.callhelp.ui.screens.ReportType
import com.totof.callhelp.ui.theme.CallHelpTheme

sealed class AppScreen {
    data object Home : AppScreen()
    data class Report(val type: ReportType) : AppScreen()
}

class MainActivity : ComponentActivity() {

    private lateinit var locationHelper: LocationHelper
    private var locationState by mutableStateOf(LocationState())
    private var currentScreen by mutableStateOf<AppScreen>(AppScreen.Home)

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { _ ->
        startLocationTracking()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        locationHelper = LocationHelper(this)

        setContent {
            CallHelpTheme {
                Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
                    when (screen) {
                        is AppScreen.Home -> {
                            HomeScreen(
                                locationState = locationState,
                                onSelectReport = { reportType ->
                                    currentScreen = AppScreen.Report(reportType)
                                },
                                onRequestLocationPermission = {
                                    requestLocationPermissions()
                                },
                            )
                        }

                        is AppScreen.Report -> {
                            when (screen.type) {
                                ReportType.INCENDIE -> {
                                    FireReportScreen(
                                        locationState = locationState,
                                        onBack = { currentScreen = AppScreen.Home },
                                        onEnableLocation = { requestLocationPermissions() },
                                    )
                                }

                                ReportType.SECOURS_PERSONNE -> {
                                    PersonHelpReportScreen(
                                        locationState = locationState,
                                        onBack = { currentScreen = AppScreen.Home },
                                        onEnableLocation = { requestLocationPermissions() },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationTracking()
    }

    override fun onPause() {
        super.onPause()
        locationHelper.stopLocationUpdates()
    }

    private fun startLocationTracking() {
        locationState = locationHelper.checkPermissionsAndLocation()
        if (locationState.isPermissionGranted && locationState.isLocationEnabled) {
            locationHelper.startLocationUpdates { newState ->
                locationState = newState
            }
        }
    }

    private fun requestLocationPermissions() {
        val check = locationHelper.checkPermissionsAndLocation()
        if (!check.isPermissionGranted) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        } else if (!check.isLocationEnabled) {
            startActivity(locationHelper.openLocationSettingsIntent())
        }
    }
}
