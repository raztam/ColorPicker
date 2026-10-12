package com.raztamim.colorpicker

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorPickerColors(
    val sheetBackground: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val title: Color,
    val subtitle: Color,
    val label: Color,
    val fieldBackground: Color,
    val fieldText: Color,
    val cancelBackground: Color,
    val cancelContent: Color,
    val applyBackground: Color,
    val applyContent: Color,
    val dragHandle: Color,
)

object ColorPickerDefaults {
    const val Title = "Select Color"
    const val Subtitle = "Display P3 / sRGB"

    fun colors(
        sheetBackground: Color = Color(0xFFF7F8FA),
        cardBackground: Color = Color.White,
        cardBorder: Color = Color(0xFFE6E8EC),
        title: Color = Color(0xFF14171C),
        subtitle: Color = Color(0xFF8A9099),
        label: Color = Color(0xFF5B6270),
        fieldBackground: Color = Color(0xFFF1F3F6),
        fieldText: Color = Color(0xFF14171C),
        cancelBackground: Color = Color(0xFFE4E9F0),
        cancelContent: Color = Color(0xFF14171C),
        applyBackground: Color = Color(0xFF0057E7),
        applyContent: Color = Color.White,
        dragHandle: Color = Color(0xFFC9CED6),
    ) = ColorPickerColors(
        sheetBackground = sheetBackground,
        cardBackground = cardBackground,
        cardBorder = cardBorder,
        title = title,
        subtitle = subtitle,
        label = label,
        fieldBackground = fieldBackground,
        fieldText = fieldText,
        cancelBackground = cancelBackground,
        cancelContent = cancelContent,
        applyBackground = applyBackground,
        applyContent = applyContent,
        dragHandle = dragHandle,
    )
}
