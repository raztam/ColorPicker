package com.raztamim.colorpicker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/** Returns `[hue, saturation, value]` with hue in `0..360` and the rest in `0..1`. */
fun Color.toHsv(): FloatArray {
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(toArgb(), hsv)
    return hsv
}

/**
 * Formats the color as `RRGGBB`, or `AARRGGBB` when it is not fully opaque.
 * The result has no leading `#`.
 */
fun Color.toHex(includeAlpha: Boolean = alpha < 1f): String {
    val argb = toArgb()
    return if (includeAlpha) {
        "%08X".format(argb)
    } else {
        "%06X".format(argb and 0xFFFFFF)
    }
}

/** Parses `RRGGBB` or `AARRGGBB`, with or without a leading `#`. */
fun String.parseHexColor(): Color? {
    val hex = trim().removePrefix("#")
    if (hex.any { it !in HEX_CHARS }) return null
    val value = hex.toLongOrNull(16) ?: return null
    return when (hex.length) {
        6 -> Color(0xFF000000 or value)
        8 -> Color(value)
        else -> null
    }
}

internal const val HEX_CHARS = "0123456789abcdefABCDEF"
