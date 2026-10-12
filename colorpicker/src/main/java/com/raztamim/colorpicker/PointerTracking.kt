package com.raztamim.colorpicker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntSize

/**
 * Reports the pointer position on press and on every move until release, consuming the events
 * so enclosing draggables (such as a bottom sheet) don't react.
 */
internal fun Modifier.trackPointer(onPosition: (Offset, IntSize) -> Unit): Modifier = composed {
    val callback by rememberUpdatedState(onPosition)
    pointerInput(Unit) {
        awaitEachGesture {
            val down = awaitFirstDown()
            down.consume()
            callback(down.position, size)
            while (true) {
                val event = awaitPointerEvent()
                val change = event.changes.firstOrNull { it.id == down.id } ?: break
                if (!change.pressed) break
                if (change.positionChange() != Offset.Zero) {
                    change.consume()
                    callback(change.position, size)
                }
            }
        }
    }
}
