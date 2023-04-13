package org.dandowney.bestpartofmyday.design.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.ui.button.FilledButton

data class DialogActionItem(
  val text: String,
  val onClick: () -> Unit,
)

@Composable
fun MultipleActionDialog(
  title: String,
  actionItems: List<DialogActionItem>,
  modifier: Modifier = Modifier,
  summary: String? = null,
  onBackgroundClick: () -> Unit = {},
) {

  Box(
    modifier = modifier
      .fillMaxSize()
      .clickableNoIndication(onClick = onBackgroundClick),
    contentAlignment = Alignment.Center,
  ) {

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(color = Gray800.copy(alpha = 0.7F)),
    )

    Column(
      modifier = Modifier
        .width(300.dp)
        .wrapContentHeight()
        .background(
          color = MaterialTheme.colors.background,
          shape = RoundedCornerShape(32.dp),
        )
        .padding(32.dp),
      verticalArrangement = spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      Text(
        text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
      )

      if (summary != null) {
        Text(
          text = summary,
          style = MaterialTheme.typography.body1,
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Start,
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      actionItems.forEach { actionItem ->
        FilledButton(
          text = actionItem.text,
          onClick = actionItem.onClick,
          modifier = Modifier.fillMaxWidth(),
        )
      }
    }
  }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MultipleActionDialogPreviews() {
  BestThemeOfMyDayPreview {
    Surface {

    }
  }
}
