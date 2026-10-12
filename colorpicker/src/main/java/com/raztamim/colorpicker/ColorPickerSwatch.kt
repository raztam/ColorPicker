package com.raztamim.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** A circle showing [color]; tapping it opens a [ColorPickerDialog]. */
@Composable
fun ColorPickerSwatch(
    color: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    title: String = ColorPickerDefaults.Title,
    subtitle: String? = ColorPickerDefaults.Subtitle,
    borderColor: Color = Color.Black.copy(alpha = 0.12f),
    borderWidth: Dp = 1.dp,
    enabled: Boolean = true,
    colors: ColorPickerColors = ColorPickerDefaults.colors(),
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
            .border(borderWidth, borderColor, CircleShape)
            .clickable(enabled = enabled, role = Role.Button) { showDialog = true }
            .semantics { contentDescription = "Selected color #${color.toHex()}" },
    )

    if (showDialog) {
        ColorPickerDialog(
            initialColor = color,
            onDismiss = { showDialog = false },
            onApply = {
                showDialog = false
                onColorSelected(it)
            },
            title = title,
            subtitle = subtitle,
            colors = colors,
        )
    }
}

@Preview
@Composable
private fun ColorPickerSwatchPreview() {
    ColorPickerSwatch(color = Color(0xFF6366F1), onColorSelected = {})
}
