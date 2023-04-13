package org.dandowney.bestpartofmyday.feature.tags.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray500
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.ui.BestTextField
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.domain.models.MoodTag
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagScreenState
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.library.colorpicker.ColorPicker
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditMoodTagScreen(
  state: MoodTagScreenState.EditMoodTag,
  onNameUpdated: (String) -> Unit,
  onColorUpdated: (HsvColor) -> Unit,
  onSaveClicked: (MoodTagScreenState.EditMoodTag) -> Unit,
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
          hsvColor = state.color,
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

        val (appBar, tagName, tagColor) = createRefs()

        BestTopAppBar(
          iconTint = Gray800,
          onBackClicked = onBackClicked,
          modifier = Modifier
            .constrainAs(appBar) {
              top.linkTo(parent.top)
              centerHorizontallyTo(parent)
            }
        )

        BestTextField(
          value = state.name,
          placeholder = "Enter a name",
          textColor = Gray800,
          placeholderColor = Gray500,
          onValueChange = { onNameUpdated(it) },
          onClearClicked = { onNameUpdated("") },
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .constrainAs(tagName) {
              top.linkTo(appBar.bottom)
              centerHorizontallyTo(parent)
            }
        )

        Column(
          modifier = Modifier
            .padding(vertical = 16.dp)
            .constrainAs(tagColor) {
              top.linkTo(tagName.bottom)
              centerHorizontallyTo(parent)
            }
        ) {

          MoodTagRow(
            state = state,
            isDarkTheme = false,
          )

          MoodTagRow(
            state = state,
            isDarkTheme = true,
          )
        }

      }
    },
  )
}

@Composable
private fun MoodTagRow(
  state: MoodTagScreenState.EditMoodTag,
  isDarkTheme: Boolean,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .background(
        color = if (isDarkTheme) Gray800 else Gray200,
      )
      .padding(horizontal = 32.dp, vertical = 16.dp),
    horizontalArrangement = spacedBy(32.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    MoodTagItem(
      item = MoodTagState(
        moodTag = MoodTag(
          moodTagId = state.id,
          name = state.name,
          color = state.color.toColor().value,
        ),
        isSelected = false,
      ),
      modifier = Modifier.weight(1F),
      isDarkTheme = isDarkTheme,
    )

    MoodTagItem(
      item = MoodTagState(
        moodTag = MoodTag(
          moodTagId = state.id,
          name = state.name,
          color = state.color.toColor().value,
        ),
        isSelected = true,
      ),
      modifier = Modifier.weight(1F),
      isDarkTheme = isDarkTheme,
    )
  }
}
