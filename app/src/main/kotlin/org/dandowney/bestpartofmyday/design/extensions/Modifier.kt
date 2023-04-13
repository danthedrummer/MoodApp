package org.dandowney.bestpartofmyday.design.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.clickableNoIndication(
  onClick: () -> Unit,
  interactionSource: MutableInteractionSource = MutableInteractionSource()
) = clickable(
  indication = null,
  interactionSource = interactionSource,
  onClick = onClick,
)
