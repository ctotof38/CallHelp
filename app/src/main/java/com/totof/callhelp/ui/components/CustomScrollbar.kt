package com.totof.callhelp.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * Custom visible vertical scrollbar for scrollable content.
 */
@Composable
fun VerticalScrollbar(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    if (scrollState.maxValue <= 0) return

    Box(
        modifier = modifier
            .width(6.dp)
            .fillMaxHeight()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.TopEnd,
    ) {
        // Track
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFE0E0E0)),
        )

        // Thumb
        Box(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight(0.3f)
                .graphicsLayer {
                    val maxVal = scrollState.maxValue.toFloat()
                    val currVal = scrollState.value.toFloat()
                    val ratio = if (maxVal > 0f) (currVal / maxVal) else 0f
                    // Translate thumb vertically within remaining height
                    translationY = ratio * (size.height * 0.7f)
                }
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFF9E9E9E)),
        )
    }
}
