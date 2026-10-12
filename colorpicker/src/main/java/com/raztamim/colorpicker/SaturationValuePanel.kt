package com.raztamim.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

private val RingRadius = 10.dp

/** Distance from the panel edge to the ring center, so the whole ring stays inside the panel. */
private val ThumbInset = RingRadius + 3.dp

@Composable
internal fun SaturationValuePanel(
    hue: Float,
    saturation: Float,
    value: Float,
    onChange: (saturation: Float, value: Float) -> Unit,
    borderColor: Color,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(14.dp)
    val insetPx = with(LocalDensity.current) { ThumbInset.toPx() }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.9f)
            .clip(shape)
            .border(1.dp, borderColor, shape)
            .trackPointer { position, size ->
                val travelX = size.width - 2 * insetPx
                val travelY = size.height - 2 * insetPx
                if (travelX > 0f && travelY > 0f) {
                    onChange(
                        (position.x - insetPx) / travelX,
                        1f - (position.y - insetPx) / travelY,
                    )
                }
            },
    ) {
        drawRect(Brush.horizontalGradient(listOf(Color.White, Color.hsv(hue, 1f, 1f))))
        drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))

        val inset = ThumbInset.toPx()
        val center = Offset(
            x = inset + saturation.coerceIn(0f, 1f) * (size.width - 2 * inset),
            y = inset + (1f - value.coerceIn(0f, 1f)) * (size.height - 2 * inset),
        )
        val ringRadius = RingRadius.toPx()
        drawCircle(Color.Black.copy(alpha = 0.25f), ringRadius + 1.dp.toPx(), center, style = Stroke(1.dp.toPx()))
        drawCircle(Color.White, ringRadius, center, style = Stroke(2.5.dp.toPx()))
        drawCircle(Color.White, 2.5.dp.toPx(), center)
    }
}
