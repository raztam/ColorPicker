package com.raztamim.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

private val HueColors = (0..6).map { Color.hsv(it * 60f % 360f, 1f, 1f) }

@Composable
internal fun HueSlider(
    hue: Float,
    onHueChange: (Float) -> Unit,
    colors: ColorPickerColors,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = "Hue",
            color = colors.label,
            fontSize = 13.sp,
        )
        HueTrack(
            hue = hue,
            onHueChange = onHueChange,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "${hue.roundToInt()}°",
            color = colors.label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            modifier = Modifier.widthIn(min = 36.dp),
        )
    }
}

@Composable
private fun HueTrack(
    hue: Float,
    onHueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val thumbRadius = 15.dp
    val thumbRadiusPx = with(LocalDensity.current) { thumbRadius.toPx() }
    Canvas(
        modifier = modifier
            .height(thumbRadius * 2 + 4.dp)
            .trackPointer { position, size ->
                val r = thumbRadiusPx
                val travel = size.width - 2 * r
                if (travel > 0f) {
                    onHueChange(((position.x - r) / travel).coerceIn(0f, 1f) * 360f)
                }
            },
    ) {
        val trackHeight = 16.dp.toPx()
        drawRoundRect(
            brush = Brush.horizontalGradient(HueColors),
            topLeft = Offset(0f, (size.height - trackHeight) / 2f),
            size = Size(size.width, trackHeight),
            cornerRadius = CornerRadius(trackHeight / 2f),
        )

        val r = thumbRadius.toPx()
        val center = Offset(
            x = r + (hue / 360f).coerceIn(0f, 1f) * (size.width - 2 * r),
            y = size.height / 2f,
        )
        drawCircle(Color.Black.copy(alpha = 0.08f), r + 2.dp.toPx(), center + Offset(0f, 1.dp.toPx()))
        drawCircle(Color.Black.copy(alpha = 0.12f), r + 0.5.dp.toPx(), center + Offset(0f, 1.dp.toPx()))
        drawCircle(Color.White, r, center)
    }
}
