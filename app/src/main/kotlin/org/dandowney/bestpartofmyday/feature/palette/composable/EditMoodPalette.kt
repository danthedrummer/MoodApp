package org.dandowney.bestpartofmyday.feature.palette.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray300
import org.dandowney.bestpartofmyday.design.theme.Gray500
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.design.ui.MinimalTextField
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteScreenState
import org.dandowney.bestpartofmyday.library.colorpicker.ColorPicker
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun EditMoodPalette(
  state: MoodPaletteScreenState.EditMoodPalette,
  onNameUpdated: (String) -> Unit,
  onColorAdded: () -> Unit,
  onColorRemoved: (Int) -> Unit,
  onColorSelected: (Int) -> Unit,
  onColorUpdated: (HsvColor) -> Unit,
  onSaveClicked: (MoodPaletteScreenState.EditMoodPalette) -> Unit,
  onBackClicked: () -> Unit,
) {

  val scaffoldState = rememberBottomSheetScaffoldState(
    bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Expanded),
  )

  BottomSheetScaffold(
    scaffoldState = scaffoldState,
    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetBackgroundColor = MaterialTheme.colors.background,
    sheetGesturesEnabled = false,
    sheetContent = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .padding(top = 32.dp),
      ) {
        ColorPicker(
          onColorChanged = { updatedColor ->
            onColorUpdated(updatedColor)
          },
          hsvColor = state.colors[state.selectedColorIndex],
          modifier = Modifier,
        )
      }
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { onSaveClicked(state) },
        modifier = Modifier
          .size(64.dp),
        backgroundColor = Gray800,
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_check),
          contentDescription = null,
          tint = Gray200,
          modifier = Modifier
            .size(24.dp),
        )
      }
    },
    content = { paddingValues ->
      ConstraintLayout(
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize()
      ) {

        val (appBar, paletteName, paletteColors) = createRefs()

        BestTopAppBar(
          iconTint = Gray800,
          onBackClicked = onBackClicked,
          modifier = Modifier
            .constrainAs(appBar) {
              top.linkTo(parent.top)
              centerHorizontallyTo(parent)
            }
        )

        MinimalTextField(
          value = state.name,
          placeholder = "Enter a palette name",
          textStyle = MaterialTheme.typography.h4,
          color = Gray800,
          placeholderColor = Gray500,
          onValueChange = { onNameUpdated(it) },
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .constrainAs(paletteName) {
              top.linkTo(appBar.bottom)
              centerHorizontallyTo(parent)
            }
        )

        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(16.dp)
            .constrainAs(paletteColors) {
              top.linkTo(paletteName.bottom)
              centerHorizontallyTo(parent)
              width = Dimension.matchParent
              height = Dimension.value(128.dp)
            },
          maxItemsInEachRow = if (state.colors.size > 5) 4 else 3,
          horizontalArrangement = spacedBy(16.dp, Alignment.CenterHorizontally),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          state.colors.forEachIndexed { index, colorValue ->
            ColorListItem(
              color = colorValue.toColor(),
              isSelected = state.selectedColorIndex == index,
              canBeRemoved = state.colors.size > 1,
              onItemClicked = {
                onColorSelected(index)
              },
              onRemoveClicked = {
                onColorRemoved(index)
              }
            )
          }

          if (state.colors.size < 7) {
            AddColorListItem(
              onClick = onColorAdded,
            )
          }
        }
      }
    },
  )
}

@Composable
fun ColorListItem(
  color: Color,
  isSelected: Boolean,
  canBeRemoved: Boolean,
  onItemClicked: () -> Unit,
  onRemoveClicked: () -> Unit,
) {
  val size by animateDpAsState(
    targetValue = when {
      isSelected -> 96.dp
      else -> 64.dp
    },
    animationSpec = tween(300),
  )
  val elevation by animateDpAsState(
    targetValue = when {
      isSelected -> 8.dp
      else -> 4.dp
    },
    animationSpec = tween(300),
  )
  val removeAlpha by animateFloatAsState(
    targetValue = when {
      isSelected -> 1F
      else -> 0F
    },
    animationSpec = tween(300),
  )

  ConstraintLayout(
    modifier = Modifier
      .height(96.dp)
      .width(size)
      .clickableNoIndication(onClick = onItemClicked),
  ) {
    val (colorContainer, removeButton) = createRefs()

    Box(
      modifier = Modifier
        .shadow(
          elevation = elevation,
          shape = CircleShape,
        )
        .background(
          color = color,
          shape = CircleShape,
        )
        .border(
          color = Gray200,
          shape = CircleShape,
          width = 1.dp,
        )
        .constrainAs(colorContainer) {
          centerTo(parent)
          height = Dimension.value(size)
          width = Dimension.value(size)
        },
    )

    if (isSelected && canBeRemoved) {
      Box(
        modifier = Modifier
          .alpha(removeAlpha)
          .wrapContentSize()
          .background(
            color = MaterialTheme.colors.background,
            shape = CircleShape
          )
          .border(
            color = Gray300,
            shape = CircleShape,
            width = 1.dp,
          )
          .padding(2.dp)
          .constrainAs(removeButton) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
          }
          .clickableNoIndication(onClick = onRemoveClicked),
        contentAlignment = Alignment.Center,
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_close),
          contentDescription = null,
          tint = Gray800,
          modifier = Modifier
            .size(24.dp)
        )
      }
    }
  }
}

@Composable
fun AddColorListItem(
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .height(96.dp)
      .width(64.dp)
      .clickableNoIndication(onClick = onClick),
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier = Modifier
        .size(64.dp)
        .shadow(
          elevation = 1.dp,
          shape = CircleShape,
        )
        .background(
          color = MaterialTheme.colors.background,
          shape = CircleShape,
        )
        .border(
          color = Gray200,
          shape = CircleShape,
          width = 1.dp,
        ),
      contentAlignment = Alignment.Center,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null,
        tint = Gray800,
        modifier = Modifier
          .size(32.dp)
      )
    }
  }
}
