package org.dandowney.bestpartofmyday.feature.today.composable

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.dandowney.bestpartofmyday.design.theme.Gray500
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.theme.textLight
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.feature.today.contract.TodayScreenState


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SelectColor(
  state: TodayScreenState.SelectColor,
  onColorSelected: (ULong) -> Unit,
  onNavigateBack: () -> Unit,
) {
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background),
  ) {
    val (appBar, question, colorOptions) = createRefs()

    BestTopAppBar(
      iconTint = Gray800,
      onBackClicked = onNavigateBack,
      modifier = Modifier
        .constrainAs(appBar) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
        }
    )

    Text(
      text = "What color are you feeling today?",
      style = MaterialTheme.typography.h2,
      color = textLight,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .constrainAs(question) {
          top.linkTo(appBar.bottom, margin = 16.dp)
          centerHorizontallyTo(parent)
        },
    )

    FlowRow(
      modifier = Modifier
        .constrainAs(colorOptions) {
          top.linkTo(question.bottom)
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start, margin = 16.dp)
          end.linkTo(parent.end, margin = 16.dp)
        },
      maxItemsInEachRow = 3,
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      state.colorPalette.forEach { colorInt ->
        ColorButton(colorInt, onColorSelected)
      }
    }
  }
}

@Composable
private fun ColorButton(
  colorInt: ULong,
  onColorSelected: (ULong) -> Unit,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressedState by interactionSource.collectIsPressedAsState()

  val transition = updateTransition(
    targetState = isPressedState,
    label = "Transition for color button pressed state",
  )
  val elevation by transition.animateDp(
    transitionSpec = { tween(200) },
    label = "Animating the color button elevation",
  ) { isPressed ->
    if (isPressed) 4.dp else 16.dp
  }
  val alpha by transition.animateFloat(
    transitionSpec = { tween(200) },
    label = "Animating a dimming layer for the color buttons",
  ) { isPressed ->
    if (isPressed) 0.1F else 0F
  }

  Box(
    modifier = Modifier
      .padding(16.dp)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = { onColorSelected(colorInt) }
      ),
  ) {
    Box(
      modifier = Modifier
        .size(64.dp)
        .shadow(
          elevation = elevation,
          shape = CircleShape,
        )
        .background(
          shape = CircleShape,
          color = Color(colorInt),
        )
        .border(
          shape = CircleShape,
          color = Gray500,
          width = Dp.Hairline,
        ),
    )
    Box(
      modifier = Modifier
        .size(64.dp)
        .alpha(alpha)
        .background(
          shape = CircleShape,
          color = Color.White,
        ),
    )
  }
}