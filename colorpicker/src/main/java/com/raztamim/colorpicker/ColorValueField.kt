package com.raztamim.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
internal fun ColorValueField(
    color: Color,
    onColorChange: (Color) -> Unit,
    colors: ColorPickerColors,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf(color.toHex()) }
    var lastTyped by remember { mutableStateOf<Color?>(null) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(color) {
        val typed = lastTyped
        if (typed == null || !typed.isCloseTo(color)) {
            text = color.toHex()
            lastTyped = null
        }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "COLOR VALUE",
            color = colors.label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.fieldBackground, RoundedCornerShape(10.dp))
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(text = "#", color = colors.subtitle, fontSize = 14.sp, fontFamily = FontFamily.Monospace)
            BasicTextField(
                value = text,
                onValueChange = { input ->
                    val filtered = input.filter { it in HEX_CHARS }.take(8).uppercase()
                    text = filtered
                    filtered.parseHexColor()?.let {
                        lastTyped = it
                        onColorChange(it)
                    }
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = colors.fieldText,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                ),
                cursorBrush = SolidColor(colors.applyBackground),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    text = color.toHex()
                }),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

private fun Color.isCloseTo(other: Color): Boolean {
    val tolerance = 1.5f / 255f
    return abs(red - other.red) < tolerance &&
        abs(green - other.green) < tolerance &&
        abs(blue - other.blue) < tolerance &&
        abs(alpha - other.alpha) < tolerance
}
