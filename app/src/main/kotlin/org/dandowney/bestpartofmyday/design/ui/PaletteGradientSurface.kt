package org.dandowney.bestpartofmyday.design.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDayPreview
import org.dandowney.bestpartofmyday.design.theme.Blue300
import org.dandowney.bestpartofmyday.design.theme.Green300
import org.dandowney.bestpartofmyday.design.theme.Indigo300
import org.dandowney.bestpartofmyday.design.theme.Orange300
import org.dandowney.bestpartofmyday.design.theme.Purple300
import org.dandowney.bestpartofmyday.design.theme.Red300
import org.dandowney.bestpartofmyday.design.theme.Yellow300

@Composable
fun PaletteGradientSurface(
  colors: List<ULong>,
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier) {
    val transparencyAlpha by animateFloatAsState(
      targetValue = when {
        colors.isNotEmpty() -> 0F
        else -> 1F
      },
      animationSpec = tween(1000),
    )

    when {
      colors.size >= 2 -> {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              brush = Brush.horizontalGradient(
                colors = colors.map(::Color),
              )
            ),
        )
      }
      colors.size == 1 -> {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              color = colors
                .first()
                .let(::Color),
            ),
        )
      }
    }

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          brush = Brush.verticalGradient(
            colorStops = arrayOf(
              0.7F to MaterialTheme.colors.background,
//              0.8F to MaterialTheme.colors.background.copy(alpha = if (transparencyAlpha == 0F) 0F else transparencyAlpha / 2F),
              1F to MaterialTheme.colors.background.copy(alpha = transparencyAlpha),
            ),
          ),
        ),
    )
  }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PaletteGradientSurfacePreviews() {
  BestThemeOfMyDayPreview {
    Surface {
      PaletteGradientSurface(
        colors = listOf(
          Red300.value,
          Orange300.value,
          Yellow300.value,
          Green300.value,
          Blue300.value,
          Indigo300.value,
          Purple300.value,
        ),
      )
    }
  }
}
