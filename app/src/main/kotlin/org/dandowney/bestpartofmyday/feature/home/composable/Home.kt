package org.dandowney.bestpartofmyday.feature.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.dandowney.bestpartofmyday.Screen
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.ui.PaletteGradientSurface
import org.dandowney.bestpartofmyday.feature.home.HomeViewModel
import org.dandowney.bestpartofmyday.feature.home.contract.HomeButtonState
import org.dandowney.bestpartofmyday.feature.home.contract.HomeEvent
import org.dandowney.bestpartofmyday.feature.home.contract.HomeScreenState

@Composable
fun Home(
  viewModel: HomeViewModel = hiltViewModel(),
  onHomeButtonClicked: (Screen) -> Unit,
) {
  LaunchedEffect(key1 = Unit) {
    viewModel.sendEvent(HomeEvent.Initialize)
  }

  val state by viewModel.state
  Home(
    state = state,
    onHomeButtonClicked = onHomeButtonClicked,
  )
}

@Composable
private fun Home(
  state: HomeScreenState,
  onHomeButtonClicked: (Screen) -> Unit,
) {
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize(),
  ) {
    val (colorGradient, buttons) = createRefs()

    PaletteGradientSurface(
      colors = state.activeColorPalette,
      modifier = Modifier
        .constrainAs(colorGradient) {
          centerTo(parent)
        }
    )

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = spacedBy(16.dp),
      horizontalArrangement = spacedBy(16.dp),
      modifier = Modifier
        .constrainAs(buttons) {
          centerTo(parent)
        }
    ) {
      items(items = state.buttons) { item ->
        HomeOptionButton(
          item = item,
          onHomeButtonClicked = onHomeButtonClicked
        )
      }
    }

  }
}

@Composable
private fun HomeOptionButton(
  item: HomeButtonState,
  modifier: Modifier = Modifier,
  onHomeButtonClicked: (Screen) -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly,
    modifier = modifier
      .fillMaxWidth()
      .height(128.dp)
      .shadow(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
      )
      .background(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colors.background,
      )
      .border(
        shape = RoundedCornerShape(16.dp),
        color = Gray200,
        width = 1.dp
      )
      .clickable(
        indication = null,
        interactionSource = MutableInteractionSource(),
        onClick = { onHomeButtonClicked(item.screen) },
      )
  ) {
    Text(
      text = item.title,
      color = Gray800,
      style = MaterialTheme.typography.h6
    )
//    Icon(
//      painter = painterResource(id = item.iconRes),
//      contentDescription = null,
//      tint = Gray800,
//      modifier = Modifier.size(32.dp),
//    )
    Icon(
      painter = painterResource(id = item.iconRes),
      contentDescription = null,
      tint = Gray800,
      modifier = Modifier.size(32.dp),
    )
  }
}
