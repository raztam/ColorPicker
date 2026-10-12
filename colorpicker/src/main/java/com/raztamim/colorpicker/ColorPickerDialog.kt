package com.raztamim.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * Bottom-sheet color picker. The selection is only reported through [onApply]; cancelling or
 * dismissing the sheet calls [onDismiss] and discards changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onDismiss: () -> Unit,
    onApply: (Color) -> Unit,
    title: String = ColorPickerDefaults.Title,
    subtitle: String? = null,
    colors: ColorPickerColors = ColorPickerDefaults.colors(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val state = rememberColorPickerState(initialColor)

    fun hideThen(action: () -> Unit) {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) action()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = colors.sheetBackground,
        dragHandle = { DragHandle(colors.dragHandle) },
    ) {
        ColorPickerContent(
            state = state,
            title = title,
            subtitle = subtitle,
            colors = colors,
            onCancel = { hideThen(onDismiss) },
            onApply = {
                val color = state.color
                hideThen { onApply(color) }
            },
        )
    }
}

@Composable
internal fun ColorPickerContent(
    state: ColorPickerState,
    title: String,
    subtitle: String?,
    colors: ColorPickerColors,
    onCancel: () -> Unit,
    onApply: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Header(title, subtitle, colors)
        HorizontalDivider(color = colors.cardBorder)

        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            SaturationValuePanel(
                hue = state.hue,
                saturation = state.saturation,
                value = state.value,
                onChange = state::updateSaturationValue,
                borderColor = colors.cardBorder,
            )

            PickerCard(colors) {
                HueSlider(
                    hue = state.hue,
                    onHueChange = state::updateHue,
                    colors = colors,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                )
            }

            PickerCard(colors) {
                ColorValueField(
                    color = state.color,
                    onColorChange = state::setColor,
                    colors = colors,
                    modifier = Modifier.padding(12.dp),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                ActionButton(
                    text = "Cancel",
                    containerColor = colors.cancelBackground,
                    contentColor = colors.cancelContent,
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                )
                ActionButton(
                    text = "Apply",
                    containerColor = colors.applyBackground,
                    contentColor = colors.applyContent,
                    onClick = onApply,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun DragHandle(color: Color) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 6.dp)
            .size(width = 36.dp, height = 5.dp)
            .background(color, CircleShape),
    )
}

@Composable
private fun Header(title: String, subtitle: String?, colors: ColorPickerColors) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = title,
            color = colors.title,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
        )
        if (subtitle != null) {
            Text(text = subtitle, color = colors.subtitle, fontSize = 11.sp)
        }
    }
}

@Composable
private fun PickerCard(colors: ColorPickerColors, content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.cardBackground, shape)
            .border(1.dp, colors.cardBorder, shape)
            .padding(4.dp),
        content = content,
    )
}

@Composable
private fun ActionButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        elevation = null,
    ) {
        Text(text = text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F8FA)
@Composable
private fun ColorPickerContentPreview() {
    ColorPickerContent(
        state = rememberColorPickerState(Color(0xFF6366F1)),
        title = ColorPickerDefaults.Title,
        subtitle = ColorPickerDefaults.Subtitle,
        colors = ColorPickerDefaults.colors(),
        onCancel = {},
        onApply = {},
    )
}
