package com.totof.callhelp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.totof.callhelp.R
import com.totof.callhelp.location.LocationState
import com.totof.callhelp.ui.components.FireIconGraphic
import com.totof.callhelp.ui.components.LocationPermissionBanner
import com.totof.callhelp.ui.components.SecoursIconGraphic
import com.totof.callhelp.ui.theme.CallHelpTheme

enum class ReportType {
    INCENDIE,
    SECOURS_PERSONNE,
}

/**
 * Main panel displaying options and zoomed pompier header image.
 */
@Composable
fun HomeScreen(
    locationState: LocationState,
    onSelectReport: (ReportType) -> Unit,
    onRequestLocationPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8F7))
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // Zoomed header image pompier.jpeg reaching close to vertical edges
                Image(
                    painter = painterResource(R.drawable.pompier),
                    contentDescription = "Pompier",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Permission/GPS warning banner if disabled
                if (!locationState.isPermissionGranted || !locationState.isLocationEnabled) {
                    LocationPermissionBanner(
                        isPermissionGranted = locationState.isPermissionGranted,
                        isLocationEnabled = locationState.isLocationEnabled,
                        onRequestPermission = onRequestLocationPermission,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Option 1: Un incendie
                ReportOptionCard(
                    title = "Un incendie",
                    iconGraphic = { FireIconGraphic() },
                    onClick = { onSelectReport(ReportType.INCENDIE) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Option 2: Un secours à personne
                ReportOptionCard(
                    title = "Un secours à personne",
                    iconGraphic = { SecoursIconGraphic() },
                    onClick = { onSelectReport(ReportType.SECOURS_PERSONNE) },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ReportOptionCard(
    title: String,
    iconGraphic: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF212121),
            )

            iconGraphic()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CallHelpTheme {
        HomeScreen(
            locationState = LocationState(
                isPermissionGranted = true,
                isLocationEnabled = true,
                dfci = "KF60C7.2",
                latitude = 45.089512,
                longitude = 5.7123941,
                lastUpdated = "23/09/2026 11:55:13",
            ),
            onSelectReport = {},
            onRequestLocationPermission = {},
        )
    }
}
