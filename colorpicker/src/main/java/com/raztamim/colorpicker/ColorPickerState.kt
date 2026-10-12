package com.raztamim.colorpicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
internal class ColorPickerState(initialColor: Color) {
    var hue by mutableFloatStateOf(0f)
        private set
    var saturation by mutableFloatStateOf(0f)
        private set
    var value by mutableFloatStateOf(0f)
        private set
    var alpha by mutableFloatStateOf(1f)
        private set

    val color: Color
        get() = Color.hsv(hue, saturation, value, alpha)

    init {
        setColor(initialColor)
    }

    fun updateHue(hue: Float) {
        this.hue = hue.coerceIn(0f, 360f)
    }

    fun updateSaturationValue(saturation: Float, value: Float) {
        this.saturation = saturation.coerceIn(0f, 1f)
        this.value = value.coerceIn(0f, 1f)
    }

    fun setColor(color: Color) {
        val (h, s, v) = color.toHsv()
        // Hue is undefined for grays and black; keep the current hue so the slider doesn't jump.
        if (s > 0f && v > 0f) hue = h
        saturation = s
        value = v
        alpha = color.alpha
    }
}

@Composable
internal fun rememberColorPickerState(initialColor: Color): ColorPickerState =
    remember { ColorPickerState(initialColor) }
