package org.dandowney.bestpartofmyday.design.ui.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDayPreview

@Composable
fun FilledButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
) {
  Button(
    text = text,
    onClick = onClick,
    modifier = modifier,
    buttonType = ButtonType.FILLED,
    enabled = enabled,
  )
}

@Composable
fun OutlinedButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
) {
  Button(
    text = text,
    onClick = onClick,
    modifier = modifier,
    buttonType = ButtonType.OUTLINED,
    enabled = enabled,
  )
}

@Composable
private fun Button(
  text: String,
  onClick: () -> Unit,
  buttonType: ButtonType,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
) {
  ButtonContainer(
    buttonType = buttonType,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  ) {
    val tintColor = when (buttonType) {
      ButtonType.FILLED -> if (enabled) {
        MaterialTheme.colors.onPrimary
      } else {
        MaterialTheme.colors.primary.copy(alpha = 0.38F)
      }
      ButtonType.OUTLINED -> if (enabled) {
        MaterialTheme.colors.primary
      } else {
        MaterialTheme.colors.primary.copy(alpha = 0.38F)
      }
      ButtonType.STANDARD -> error("Not supported by this type of button")
    }

    Text(
      text = text,
      style = MaterialTheme.typography.button,
      color = tintColor,
      textAlign = TextAlign.Center,
      maxLines = 1,
      modifier = Modifier.padding(horizontal = 8.dp),
    )
  }
}

@Composable
private fun ButtonContainer(
  buttonType: ButtonType,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  buttonContent: @Composable RowScope.() -> Unit,
) {
  Row(
    modifier = modifier
      .height(40.dp)
      .clickableNoIndication(onClick = onClick)
      .let {
        when (buttonType) {
          ButtonType.FILLED -> it.asFilledButton(enabled = enabled)
          ButtonType.OUTLINED -> it.asOutlinedButton(enabled = enabled)
          ButtonType.STANDARD -> error("Not supported by this type of button")
        }
      }
      .padding(
        vertical = 8.dp,
        horizontal = 16.dp,
      ),
    verticalAlignment = Alignment.CenterVertically,
    content = buttonContent
  )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ButtonPreviews() {
  BestThemeOfMyDayPreview {
    Surface {
      Column(
        verticalArrangement = Arrangement.Absolute.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
      ) {
        Row(
          horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp)
        ) {
          FilledButton(
            text = "Action",
            onClick = {},
          )

          FilledButton(
            text = "Action",
            onClick = {},
            enabled = false,
          )
        }

        Row(
          horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp)
        ) {
          OutlinedButton(
            text = "Action",
            onClick = {},
          )

          OutlinedButton(
            text = "Action",
            onClick = {},
            enabled = false,
          )
        }
      }
    }
  }
}
