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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
 * Screen matching 03.png (Signaler un secours à personne).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonHelpReportScreen(
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
                        text = "Signaler un secours à person...",
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

                // Card 3: Instructions for Person Help report (03.png)
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
                        // Section 1: Transmettre l'alerte
                        Text(
                            text = "Transmettre l'alerte",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Victime ou témoin d'un accident vous devez contacter les services d'urgence et donner les indications suivantes :",
                            fontSize = 15.sp,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Bullets
                        BulletFormatted(
                            normalPrefix = "- Vous présenter en donnant le ",
                            boldText = "numéro de téléphone",
                            normalSuffix = "",
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        BulletFormatted(
                            normalPrefix = "- La ",
                            boldText = "nature du problème",
                            normalSuffix = " , maladie ou accidents",
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        BulletFormatted(
                            normalPrefix = "- Les ",
                            boldText = "risques",
                            normalSuffix = " éventuels : incendie, explosion, effondrement, produits chimiques et tout autre danger",
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        BulletFormatted(
                            normalPrefix = "- La ",
                            boldText = "localisation",
                            normalSuffix = " très précise de l'évènement",
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        BulletFormatted(
                            normalPrefix = "- Le ",
                            boldText = "nombre",
                            normalSuffix = " de personnes concernées",
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        BulletFormatted(
                            normalPrefix = "- La ",
                            boldText = "gravité",
                            normalSuffix = " de l'état de la ou des victimes",
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Wait instructions paragraph
                        Text(
                            text = buildAnnotatedString {
                                append("Le message d'alerte achevé, l'appelant doit ")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("attendre les instructions avant d'interrompre la communication")
                                }
                            },
                            fontSize = 15.sp,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        // Section 2: Pourquoi transmettre l'alerte ?
                        Text(
                            text = "Pourquoi transmettre l'alerte ?",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "L'alerte, transmise au service d'urgence par les moyens les plus appropriés disponibles, doit être rapide et précise pour diminuer les délais d'arrivée des secours nécessaire.",
                            fontSize = 15.sp,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Tout retard et toute imprécision peuvent concourir à l'aggravation de l'état de la victime.",
                            fontSize = 15.sp,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        // Section 3: Choisir le bon service de secours :
                        Text(
                            text = "Choisir le bon service de secours :",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // 18
                        ServiceItem(
                            number = "Le 18",
                            target = "sapeurs-pompiers",
                            desc = " pour tout problème de secours, notamment accident et incendies.",
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // 15
                        ServiceItem(
                            number = "Le 15",
                            target = "samu",
                            desc = " pour tout problème urgent de santé. c'est un secours médicalisé",
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // 17
                        ServiceItem(
                            number = "Le 17",
                            target = "police ou la gendarmerie",
                            desc = " pour tout problème de sécurité ou d'ordre public",
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // 112
                        ServiceItem(
                            number = "Le 112",
                            target = "numéro d'appel unique des urgences sur le territoire Européen",
                            desc = ", recommandé aux étrangers circulant en France et aux Français circulant à l'étranger. En France, ce numéro ne se substitue pas aux autres numéros d'urgences.",
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // 115
                        ServiceItem(
                            number = "Le 115",
                            target = "samu social",
                            desc = " pour toutes les personnes qui présentent une détresse sociale comme les personnes sans domicile ou sans abris exposées aux intempéries.",
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Free call paragraph
                        Text(
                            text = buildAnnotatedString {
                                append("L'appel à ces numéros est ")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("gratuit")
                                }
                                append(" et possible sur tout appareil raccordé aux réseau téléphonique national même en l'absence de monnaies ou de carte téléphonique et de code PIN pour les téléphones mobiles (seulement pour le 112)")
                            },
                            fontSize = 15.sp,
                            color = Color.Black,
                        )
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
private fun BulletFormatted(
    normalPrefix: String,
    boldText: String,
    normalSuffix: String,
) {
    Text(
        text = buildAnnotatedString {
            append(normalPrefix)
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(boldText)
            }
            append(normalSuffix)
        },
        fontSize = 15.sp,
        color = Color.Black,
    )
}

@Composable
private fun ServiceItem(
    number: String,
    target: String,
    desc: String,
) {
    Row(
        verticalAlignment = Alignment.Top,
    ) {
        Text(text = "- ", fontSize = 15.sp, color = Color.Black)
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(number)
                }
                append(" : les ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(target)
                }
                append(desc)
            },
            fontSize = 15.sp,
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PersonHelpReportScreenPreview() {
    CallHelpTheme {
        PersonHelpReportScreen(
            locationState = LocationState(
                isPermissionGranted = true,
                isLocationEnabled = true,
                dfci = "KF60C7.2",
                latitude = 45.089519,
                longitude = 5.7123878,
                lastUpdated = "23/09/2026 12:01:28",
            ),
            onBack = {},
            onEnableLocation = {},
        )
    }
}
