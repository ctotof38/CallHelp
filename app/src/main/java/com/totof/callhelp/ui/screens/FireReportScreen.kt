package com.totof.callhelp.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.totof.callhelp.location.LocationState
import com.totof.callhelp.ui.components.BackArrowIcon
import com.totof.callhelp.ui.components.DfciPositionCard
import com.totof.callhelp.ui.components.EmergencyCallButtonsCard
import com.totof.callhelp.ui.components.VerticalScrollbar
import com.totof.callhelp.ui.theme.CallHelpTheme

/**
 * Screen matching 02.png (Signaler un incendie).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FireReportScreen(
    locationState: LocationState,
    onBack: () -> Unit,
    onEnableLocation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Signaler un incendie",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        BackArrowIcon(tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                ),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
            ) {
                // Card 1: DFCI position
                DfciPositionCard(
                    locationState = locationState,
                    onEnableLocation = onEnableLocation,
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Card 2: Call buttons (18 & 112)
                EmergencyCallButtonsCard()

                Spacer(modifier = Modifier.height(14.dp))

                // Card 3: Instructions for Fire report
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                    ) {
                        Text(
                            text = "Soyez rapide pour alerter, précis pour localiser, clair pour renseigner.",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Des précisions vous seront demandées:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        BulletItem(text = "Le lieu et cause éventuelle")
                        Spacer(modifier = Modifier.height(12.dp))
                        BulletItem(text = "L'importance des dégâts")
                        Spacer(modifier = Modifier.height(12.dp))
                        BulletItem(text = "Les menaces possibles")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Visible scrollbar
            VerticalScrollbar(
                scrollState = scrollState,
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
    }
}

@Composable
private fun BulletItem(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "- ",
            fontSize = 16.sp,
            color = Color.Black,
        )
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FireReportScreenPreview() {
    CallHelpTheme {
        FireReportScreen(
            locationState = LocationState(
                isPermissionGranted = true,
                isLocationEnabled = true,
                dfci = "KF60C7.2",
                latitude = 45.089512,
                longitude = 5.7123941,
                lastUpdated = "23/09/2026 11:55:13",
            ),
            onBack = {},
            onEnableLocation = {},
        )
    }
}
