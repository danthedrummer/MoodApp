package org.dandowney.bestpartofmyday.design.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDay
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDayPreview

/**
 * Full screen circular progress indicator
 */
@Composable
internal fun FullScreenLoading(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center)
  ) {
    CircularProgressIndicator(
      color = MaterialTheme.colors.primary,
    )
  }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun FullScreenLoadingPreview() {
  BestThemeOfMyDayPreview {
    Surface(
      modifier = Modifier.size(100.dp)
    ) {
      FullScreenLoading()
    }
  }
}
