package com.totof.callhelp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.totof.callhelp.R

/**
 * Graphic for the home screen top logo showing a smartphone with 112 / 18 on a red screen.
 * Matches 01.png graphic.
 */
@Composable
fun HeaderSmartphoneGraphic(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 160.dp, height = 200.dp)
            .rotate(-8f),
        contentAlignment = Alignment.Center,
    ) {
        // Outer dark phone body
        Box(
            modifier = Modifier
                .size(width = 130.dp, height = 180.dp)
                .shadow(8.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF333333))
                .padding(10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Top speaker
                Box(
                    modifier = Modifier
                        .size(width = 24.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF666666)),
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Red Phone Screen
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFD32F2F)),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "112",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                        )
                        Box(
                            modifier = Modifier
                                .size(width = 60.dp, height = 2.dp)
                                .background(Color.White),
                        )
                        Text(
                            text = "18",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Bottom Home button dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF888888)),
                )
            }
        }
    }
}

/**
 * Fire graphic icon for "Un incendie" option using fire_icon.png.
 */
@Composable
fun FireIconGraphic(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF4DD0E1)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.fire_icon),
            contentDescription = "Incendie",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(38.dp),
        )
    }
}

/**
 * Help icon graphic for "Un secours à personne" option using man_icon.png.
 */
@Composable
fun SecoursIconGraphic(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF26A69A)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.man_icon),
            contentDescription = "Secours à personne",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(38.dp),
        )
    }
}

/**
 * Custom Back Arrow icon for TopAppBar.
 */
@Composable
fun BackArrowIcon(modifier: Modifier = Modifier, tint: Color = Color.White) {
    Canvas(modifier = modifier.size(24.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.82f, size.height * 0.50f)
            lineTo(size.width * 0.18f, size.height * 0.50f)
            moveTo(size.width * 0.46f, size.height * 0.22f)
            lineTo(size.width * 0.18f, size.height * 0.50f)
            lineTo(size.width * 0.46f, size.height * 0.78f)
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(width = 6f, cap = StrokeCap.Round, join = StrokeJoin.Round),
        )
    }
}
