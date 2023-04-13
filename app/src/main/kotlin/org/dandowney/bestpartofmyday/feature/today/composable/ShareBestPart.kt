package org.dandowney.bestpartofmyday.feature.today.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.theme.isDark
import org.dandowney.bestpartofmyday.design.theme.placeholderTextDark
import org.dandowney.bestpartofmyday.design.theme.placeholderTextLight
import org.dandowney.bestpartofmyday.design.theme.textDark
import org.dandowney.bestpartofmyday.design.theme.textLight
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.design.ui.MinimalTextField
import org.dandowney.bestpartofmyday.feature.today.contract.TodayScreenState


@Composable
internal fun ShareBestPart(
  state: TodayScreenState.ShareBestPart,
  onBackClicked: () -> Unit,
  onBestPartUpdated: (String) -> Unit,
  onBestPartSaved: () -> Unit,
) {

  val targetBackgroundColor = remember { Color(state.backgroundColor) }
  var animating by remember {
    mutableStateOf(false)
  }
  val backgroundColor by animateColorAsState(
    targetValue = when {
      !state.shouldAnimate || animating -> targetBackgroundColor
      else -> MaterialTheme.colors.background
    },
    animationSpec = tween(1000),
  )
  val contrastingColor by animateColorAsState(
    targetValue = when {
      (state.shouldAnimate && targetBackgroundColor.isDark()) || targetBackgroundColor.isDark() -> textDark
      else -> textLight
    },
    animationSpec = tween(1000),
  )
  val contrastingPlaceholderColor by animateColorAsState(
    targetValue = when {
      (state.shouldAnimate && targetBackgroundColor.isDark()) || targetBackgroundColor.isDark() -> placeholderTextDark
      else -> placeholderTextLight
    },
    animationSpec = tween(1000),
  )

  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(color = backgroundColor),
  ) {

    val (appBar, question, entry, save) = createRefs()

    BestTopAppBar(
      iconTint = contrastingColor,
      onBackClicked = onBackClicked,
      modifier = Modifier
        .constrainAs(appBar) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
        }
    )

    Text(
      text = "What was the best part of your day?",
      style = MaterialTheme.typography.h2,
      color = contrastingColor,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .constrainAs(question) {
          top.linkTo(appBar.bottom, margin = 16.dp)
          centerHorizontallyTo(parent)
        },
    )

    MinimalTextField(
      value = state.bestPart,
      placeholder = "Tell me about something interesting...",
      color = contrastingColor,
      placeholderColor = contrastingPlaceholderColor,
      textStyle = MaterialTheme.typography.h4,
      onValueChange = { onBestPartUpdated(it) },
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 16.dp)
        .constrainAs(entry) {
          top.linkTo(question.bottom)
          bottom.linkTo(parent.bottom)
          centerHorizontallyTo(parent)
        },
    )

    FloatingActionButton(
      onClick = onBestPartSaved,
      modifier = Modifier
        .padding(32.dp)
        .size(64.dp)
        .constrainAs(save) {
          bottom.linkTo(parent.bottom)
          end.linkTo(parent.end)
        },
      backgroundColor = contrastingColor,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_forward),
        contentDescription = null,
        tint = backgroundColor,
        modifier = Modifier
          .size(24.dp),
      )
    }
  }

  LaunchedEffect(key1 = state.backgroundColor) {
    animating = state.shouldAnimate
  }
}