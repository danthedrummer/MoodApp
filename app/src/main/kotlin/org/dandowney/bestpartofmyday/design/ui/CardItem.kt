package org.dandowney.bestpartofmyday.design.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDayPreview
import org.dandowney.bestpartofmyday.design.ui.button.OutlinedButton
import org.dandowney.bestpartofmyday.design.ui.button.StandardIconButton

sealed interface CardActionItem {
  data class Text(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
  ) : CardActionItem

  data class Icon(
    val icon: Painter,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
  ) : CardActionItem
}

@Composable
fun CardItem(
  modifier: Modifier = Modifier,
  image: @Composable RowScope.() -> Unit = {},
  headline: String? = null,
  subheading: String? = null,
  summary: String? = null,
  actions: List<CardActionItem> = emptyList(),
  actionAlignment: Alignment.Horizontal = Alignment.Start,
  selected: Boolean = false,
) {
  val elevation by animateDpAsState(
    targetValue = if (selected) 8.dp else 2.dp,
    animationSpec = tween(300),
  )
  val outlineColor by animateColorAsState(
    targetValue = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
    animationSpec = tween(300),
  )
  Column(
    verticalArrangement = spacedBy(8.dp),
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .shadow(
        elevation = elevation,
        shape = RoundedCornerShape(16.dp),
      )
      .background(
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(16.dp),
      )
      .border(
        shape = RoundedCornerShape(16.dp),
        color = outlineColor,
        width = 1.dp,
      ),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(RoundedCornerShape(16.dp)),
      content = image,
    )

    Column(
      modifier = Modifier
        .padding(horizontal = 16.dp),
      verticalArrangement = spacedBy(8.dp),
    ) {
      if (headline != null) {
        AutoResizeText(
          text = headline,
          fontSizeRange = MaterialTheme.typography.h6.toFontSizeRange(shrink = 2.sp),
          style = MaterialTheme.typography.h6,
        )
      }

      if (subheading != null) {
        AutoResizeText(
          text = subheading,
          fontSizeRange = MaterialTheme.typography.body2.toFontSizeRange(shrink = 2.sp),
          style = MaterialTheme.typography.body2,
        )
      }

      if (summary != null) {
        AutoResizeText(
          text = summary,
          fontSizeRange = MaterialTheme.typography.caption.toFontSizeRange(shrink = 2.sp),
          style = MaterialTheme.typography.caption,
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp),
        horizontalArrangement = spacedBy(4.dp, alignment = actionAlignment),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        actions.forEach { actionItem ->
          CardActionButton(actionItem = actionItem)
        }
      }
    }
  }
}

@Composable
private fun CardActionButton(
  actionItem: CardActionItem,
) {
  when (actionItem) {
    is CardActionItem.Icon -> {
      StandardIconButton(
        icon = actionItem.icon,
        onClick = actionItem.onClick,
        enabled = actionItem.enabled,
      )
    }
    is CardActionItem.Text -> {
      OutlinedButton(
        text = actionItem.text,
        onClick = actionItem.onClick,
      )
    }
  }
}

private fun TextStyle.toFontSizeRange(shrink: TextUnit): FontSizeRange = FontSizeRange(
  max = fontSize,
  min = (fontSize.value - shrink.value).sp,
)

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CardItemPreviews() {
  BestThemeOfMyDayPreview {
    Surface {
      LazyVerticalGrid(columns = GridCells.Fixed(2)) {

        item {
          CardItem(
            modifier = Modifier.padding(32.dp),
            image = {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(32.dp)
                  .background(
                    color = Color.Red,
                  ),
              )
            },
            headline = "Example",
            subheading = "Sample card",
            summary = "A fancy example of a card item",
            actions = listOf(
              CardActionItem.Text(
                text = "Action",
                onClick = {},
              ),
              CardActionItem.Icon(
                icon = painterResource(id = R.drawable.ic_check),
                onClick = {},
              ),
            ),
          )
        }


        item {
          CardItem(
            modifier = Modifier.padding(32.dp),
            image = {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(32.dp)
                  .background(
                    color = Color.Red,
                  ),
              )
            },
            headline = "Example",
            subheading = "Sample card",
            summary = "A fancy example of a card item",
            actions = listOf(
              CardActionItem.Text(
                text = "Action",
                onClick = {},
              ),
              CardActionItem.Icon(
                icon = painterResource(id = R.drawable.ic_check),
                onClick = {},
              ),
            ),
            selected = true,
          )
        }

      }
    }
  }
}
