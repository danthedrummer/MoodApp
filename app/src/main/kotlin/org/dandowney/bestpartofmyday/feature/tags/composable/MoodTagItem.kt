package org.dandowney.bestpartofmyday.feature.tags.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.theme.isAlmostBlack
import org.dandowney.bestpartofmyday.design.theme.isAlmostWhite
import org.dandowney.bestpartofmyday.design.theme.isDark
import org.dandowney.bestpartofmyday.design.theme.isDarkTheme
import org.dandowney.bestpartofmyday.design.ui.AutoResizeText
import org.dandowney.bestpartofmyday.design.ui.FontSizeRange
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState

@Composable
fun MoodTagItem(
  item: MoodTagState,
  modifier: Modifier = Modifier,
  onTagClicked: (MoodTagState) -> Unit = {},
  isDarkTheme: Boolean = isDarkTheme(),
) {
  val tagColor = Color(item.moodTag.color)
  val textColor by animateColorAsState(
    targetValue = when {
      item.isSelected && tagColor.isDark() -> Gray200
      item.isSelected -> Gray800
      isDarkTheme -> Gray800
      else -> Gray200
    },
    animationSpec = tween(300),
  )
  val backgroundHeight by animateDpAsState(
    targetValue = when {
      item.isSelected -> 64.dp
      else -> 24.dp
    },
    animationSpec = tween(300)
  )
  val elevation by animateDpAsState(
    targetValue = when {
      item.isSelected -> 2.dp
      else -> 8.dp
    }
  )
  ConstraintLayout(
    modifier = modifier
      .wrapContentWidth()
      .height(64.dp)
      .shadow(
        elevation = elevation,
        shape = RoundedCornerShape(16.dp),
      )
      .background(
        color = if (isDarkTheme) Gray200 else Gray800,
//        color = if (isDarkTheme) Gray800 else Gray200,
        shape = RoundedCornerShape(16.dp),
      )
      .border(
        shape = RoundedCornerShape(16.dp),
        color = if (isDarkTheme) Gray200 else Gray800,
//        color = if (isDarkTheme) Gray800 else Gray200,
        width = 1.dp,
      )
      .clickableNoIndication(
        onClick = { onTagClicked(item) }
      ),
  ) {

    val (background, nameLabel, selectedGradient) = createRefs()

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(backgroundHeight)
        .background(
          color = tagColor,
          shape = RoundedCornerShape(16.dp),
        )
        .constrainAs(background) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
        }
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(backgroundHeight)
        .background(
          brush = Brush.verticalGradient(
            colorStops = arrayOf(
              0.8F to Color.Transparent,
              1F to when {
                !isDarkTheme && tagColor.isAlmostBlack() -> Gray200.copy(alpha = 0.3F)
                isDarkTheme && tagColor.isAlmostWhite() -> Gray800.copy(alpha = 0.5F)
                else -> Color.Transparent
              },
            )
          ),
          shape = RoundedCornerShape(16.dp),
        )
        .constrainAs(selectedGradient) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
        },
    )

    AutoResizeText(
      text = item.moodTag.name,
      fontSizeRange = FontSizeRange(
        min = 10.sp,
        max = 14.sp,
      ),
      style = MaterialTheme.typography.subtitle2,
      textAlign = TextAlign.Start,
      color = textColor,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(vertical = 8.dp)
        .constrainAs(nameLabel) {
          bottom.linkTo(parent.bottom)
          centerHorizontallyTo(parent)
        },
    )

  }
}
