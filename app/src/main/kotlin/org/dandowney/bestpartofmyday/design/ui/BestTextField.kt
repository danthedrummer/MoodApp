package org.dandowney.bestpartofmyday.design.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.Gray800

@Composable
fun BestTextField(
  value: String,
  placeholder: String,
  textColor: Color,
  placeholderColor: Color,
  onValueChange: (String) -> Unit,
  onClearClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .border(
        shape = RoundedCornerShape(8.dp),
        width = 1.dp,
        color = Gray800,
      ),
    contentAlignment = Alignment.TopStart,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      MinimalTextField(
        value = value,
        placeholder = placeholder,
        textStyle = MaterialTheme.typography.h4,
        color = textColor,
        placeholderColor = placeholderColor,
        onValueChange = onValueChange,
      )

      if (value.isNotEmpty()) {
        Icon(
          painter = painterResource(id = R.drawable.ic_close),
          contentDescription = null,
          modifier = Modifier
            .padding(end = 16.dp)
            .size(24.dp)
            .clickableNoIndication(onClick = onClearClicked),
        )
      }
    }
  }
}
