package org.dandowney.bestpartofmyday.design.ui.button

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDay
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDayPreview

@Composable
fun StandardIconButton(
  icon: Painter,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  enabled: Boolean = true,
) {
  IconButton(
    icon = icon,
    buttonType = ButtonType.STANDARD,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  )
}

@Composable
fun FilledIconButton(
  icon: Painter,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  enabled: Boolean = true,
) {
  IconButton(
    icon = icon,
    buttonType = ButtonType.FILLED,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  )
}

@Composable
fun OutlinedIconButton(
  icon: Painter,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  enabled: Boolean = true,
) {
  IconButton(
    icon = icon,
    buttonType = ButtonType.OUTLINED,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  )
}

@Composable
private fun IconButton(
  icon: Painter,
  buttonType: ButtonType,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  IconButtonContainer(
    buttonType = buttonType,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  ) {
    Icon(
      painter = icon,
      contentDescription = null,
      modifier = Modifier
        .size(24.dp),
      tint = when (buttonType) {
        ButtonType.FILLED -> {
          if (enabled) {
            MaterialTheme.colors.onPrimary
          } else {
            MaterialTheme.colors.onSurface.copy(alpha = 0.38F)
          }
        }
        ButtonType.OUTLINED -> {
          if (enabled) {
            MaterialTheme.colors.onSurface
          } else {
            MaterialTheme.colors.onSurface.copy(alpha = 0.38F)
          }
        }
        ButtonType.STANDARD -> {
          if (enabled) {
            MaterialTheme.colors.onSurface
          } else {
            MaterialTheme.colors.onSurface.copy(alpha = 0.38F)
          }
        }
      },
    )
  }
}

@Composable
private fun IconButtonContainer(
  buttonType: ButtonType,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  buttonContent: @Composable RowScope.() -> Unit,
) {

  Row(
    modifier = modifier
      .wrapContentWidth()
      .clickableNoIndication(onClick = onClick)
      .let {
        when (buttonType) {
          ButtonType.FILLED -> it.asFilledButton(enabled = enabled)
          ButtonType.OUTLINED -> it.asOutlinedButton(enabled = enabled)
          ButtonType.STANDARD -> it
        }
      }
      .padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
    content = buttonContent
  )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ButtonPreviews() {
  BestThemeOfMyDayPreview {
    Surface {
      Column(
        verticalArrangement = spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
      ) {
        Row(
          horizontalArrangement = spacedBy(8.dp)
        ) {
          FilledIconButton(
            icon = painterResource(id = R.drawable.ic_check),
            onClick = {},
          )

          FilledIconButton(
            icon = painterResource(id = R.drawable.ic_check),
            onClick = {},
            enabled = false,
          )
        }

        Row(
          horizontalArrangement = spacedBy(8.dp)
        ) {
          OutlinedIconButton(
            icon = painterResource(id = R.drawable.ic_check),
            onClick = {},
          )

          OutlinedIconButton(
            icon = painterResource(id = R.drawable.ic_check),
            onClick = {},
            enabled = false,
          )
        }

        Row(
          horizontalArrangement = spacedBy(8.dp)
        ) {
          StandardIconButton(
            icon = painterResource(id = R.drawable.ic_check),
            onClick = {},
          )

          StandardIconButton(
            icon = painterResource(id = R.drawable.ic_check),
            onClick = {},
            enabled = false,
          )
        }
      }
    }
  }
}
