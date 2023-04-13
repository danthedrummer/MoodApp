package org.dandowney.bestpartofmyday.design.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication

@Composable
fun BestTopAppBar(
  iconTint: Color,
  onBackClicked: () -> Unit,
  modifier: Modifier = Modifier,
  backgroundColor: Color = Color.Transparent,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(72.dp)
      .background(color = backgroundColor)
      .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_back),
      contentDescription = null,
      tint = iconTint,
      modifier = Modifier
        .size(24.dp)
        .clickableNoIndication(onClick = onBackClicked),
    )
  }
}
