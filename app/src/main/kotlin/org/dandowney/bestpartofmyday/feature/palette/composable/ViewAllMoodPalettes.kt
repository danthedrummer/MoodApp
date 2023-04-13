package org.dandowney.bestpartofmyday.feature.palette.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.selects.select
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.BestThemeOfMyDay
import org.dandowney.bestpartofmyday.design.theme.Blue300
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.theme.Green300
import org.dandowney.bestpartofmyday.design.theme.Red300
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.design.ui.CardActionItem
import org.dandowney.bestpartofmyday.design.ui.CardItem
import org.dandowney.bestpartofmyday.design.ui.PaletteGradientSurface
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteScreenState
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteState

@Composable
fun ViewAllMoodPalettes(
  state: MoodPaletteScreenState.ViewAllMoodPalettes,
  onEditClicked: (MoodPaletteState) -> Unit,
  onActivateClicked: (MoodPaletteState) -> Unit,
  onAddPaletteClicked: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  PaletteGradientSurface(
    colors = state.activeColors,
    modifier = Modifier.fillMaxSize(),
  )
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize(),
  ) {

    val (appBar, palettes, fab) = createRefs()

    BestTopAppBar(
      iconTint = Gray800,
      onBackClicked = onNavigateBack,
      modifier = Modifier
        .constrainAs(appBar) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
        },
    )

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier
        .constrainAs(palettes) {
          top.linkTo(appBar.bottom)
          bottom.linkTo(parent.bottom)
          centerHorizontallyTo(parent)
          height = Dimension.fillToConstraints
          width = Dimension.matchParent
        },
      contentPadding = PaddingValues(
        start = 16.dp,
        top = 16.dp,
        end = 16.dp,
        bottom = 128.dp,
      ),
      verticalArrangement = spacedBy(16.dp),
      horizontalArrangement = spacedBy(16.dp)
    ) {
      items(items = state.moodPalettes) { moodPalette ->
        MoodPaletteCardItem(
          moodPalette = moodPalette,
          onEditClicked = onEditClicked,
          onActivateClicked = onActivateClicked,
          canBeDeleted = state.moodPalettes.size > 1,
        )
      }
    }

    FloatingActionButton(
      onClick = onAddPaletteClicked,
      modifier = Modifier
        .size(64.dp)
        .constrainAs(fab) {
          bottom.linkTo(parent.bottom, margin = 32.dp)
          end.linkTo(parent.end, margin = 32.dp)
        },
      backgroundColor = Gray800,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null,
        tint = Gray200,
        modifier = Modifier
          .size(24.dp),
      )
    }

  }
}

@Composable
fun MoodPaletteCardItem(
  moodPalette: MoodPaletteState,
  onEditClicked: (MoodPaletteState) -> Unit,
  onActivateClicked: (MoodPaletteState) -> Unit,
  canBeDeleted: Boolean,
  modifier: Modifier = Modifier,
) {

  val gradientAlpha by animateFloatAsState(
    targetValue = if (moodPalette.isActive) 0F else 0.3F,
    animationSpec = tween(300)
  )

  CardItem(
    modifier = modifier.clickableNoIndication(onClick = { onActivateClicked(moodPalette) }),
    headline = moodPalette.name,
    actionAlignment = Alignment.End,
    selected = moodPalette.isActive,
    actions = listOf(
//      CardActionItem.Icon(
//        icon = painterResource(id = R.drawable.ic_power),
//        onClick = { onActivateClicked(moodPalette) },
//        enabled = !moodPalette.isActive,
//      ),
      CardActionItem.Icon(
        icon = painterResource(id = R.drawable.ic_create),
        onClick = { onEditClicked(moodPalette) },
      ),
      CardActionItem.Icon(
        icon = painterResource(id = R.drawable.ic_delete),
        onClick = { onEditClicked(moodPalette) },
        enabled = canBeDeleted,
      ),
    ),
    image = {
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
      ) {
        if (moodPalette.colors.size >= 2) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(96.dp)
              .background(
                brush = Brush.horizontalGradient(
                  colors = moodPalette.colors.map(::Color),
                ),
                shape = RoundedCornerShape(8.dp),
              )
          )
        } else {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(96.dp)
              .background(
                color = moodPalette.colors
                  .firstOrNull()
                  ?.let(::Color) ?: Color.White,
                shape = RoundedCornerShape(8.dp),
              )
          )
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .background(
              color = Gray800.copy(alpha = gradientAlpha),
              shape = RoundedCornerShape(8.dp),
            )
        )
      }
    },
  )
}

@Preview(showBackground = true)
@Composable
fun ViewAllMoodPalettesPreview() {
  BestThemeOfMyDay(darkTheme = false) {
    ViewAllMoodPalettes(
      state = MoodPaletteScreenState.ViewAllMoodPalettes(
        moodPalettes = listOf(
          org.dandowney.bestpartofmyday.domain.models.MoodPalette(
            id = 0L,
            name = "Default",
            colors = listOf(Red300.value, Blue300.value, Green300.value),
            isActive = true,
          ).let {
            MoodPaletteState(
              name = it.name,
              colors = it.colors,
              isActive = it.isActive,
              domain = it,
            )
          }
        ),
        activeColors = emptyList(),
      ),
      onEditClicked = {},
      onActivateClicked = {},
      onAddPaletteClicked = {},
      onNavigateBack = {},
    )
  }
}
