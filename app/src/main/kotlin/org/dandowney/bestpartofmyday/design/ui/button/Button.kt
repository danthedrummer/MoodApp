package org.dandowney.bestpartofmyday.design.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

enum class ButtonType {
  FILLED,
  OUTLINED,
  STANDARD,
}

enum class ButtonIconAlignment {
  START,
  END,
}

fun Modifier.asFilledButton(enabled: Boolean) = composed {
  this
    .background(
      color = if (enabled) {
        MaterialTheme.colors.primary
      } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.12F)
      },
      shape = CircleShape,
    )
}

fun Modifier.asOutlinedButton(enabled: Boolean) = composed {
  this
    .border(
      color = if (enabled) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(alpha = 0.12F),
      shape = CircleShape,
      width = 1.dp,
    )
}
