package org.dandowney.bestpartofmyday.feature.tags.composable

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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.design.ui.CardActionItem
import org.dandowney.bestpartofmyday.design.ui.CardItem
import org.dandowney.bestpartofmyday.design.ui.DialogActionItem
import org.dandowney.bestpartofmyday.design.ui.MultipleActionDialog
import org.dandowney.bestpartofmyday.design.ui.PaletteGradientSurface
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagScreenState
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState

@Composable
fun ViewAllMoodTagsScreen(
  state: MoodTagScreenState.ViewAllMoodTags,
  onBackClicked: () -> Unit,
  onMoodTagClicked: (MoodTagState) -> Unit,
  onAddMoodTagClicked: () -> Unit,
  onDeleteClick: (MoodTagState) -> Unit,
  onArchiveConfirm: (MoodTagState) -> Unit,
  onDeleteConfirm: (MoodTagState) -> Unit,
  onCloseDialog: () -> Unit,
) {

  PaletteGradientSurface(
    colors = state.activeColors,
    modifier = Modifier.fillMaxSize(),
  )

  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize(),
  ) {

    val (appBar, tagsGrid, fab) = createRefs()

    BestTopAppBar(
      iconTint = Gray800,
      onBackClicked = onBackClicked,
      modifier = Modifier
        .constrainAs(appBar) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
        },
    )

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      verticalArrangement = spacedBy(16.dp),
      horizontalArrangement = spacedBy(16.dp),
      modifier = Modifier.constrainAs(tagsGrid) {
        top.linkTo(appBar.bottom, margin = 16.dp)
        bottom.linkTo(parent.bottom)
        centerHorizontallyTo(parent)
        height = Dimension.fillToConstraints
      },
      contentPadding = PaddingValues(
        start = 16.dp,
        top = 16.dp,
        end = 16.dp,
        bottom = 128.dp,
      ),
    ) {

      items(items = state.moodTags) { moodTag ->
        CardItem(
          image = {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                  color = Color(moodTag.moodTag.color),
                )
            )
          },
          headline = moodTag.moodTag.name,
          actionAlignment = Alignment.End,
          actions = listOf(
            CardActionItem.Icon(
              icon = painterResource(id = R.drawable.ic_create),
              onClick = { onMoodTagClicked(moodTag) },
            ),
            CardActionItem.Icon(
              icon = painterResource(id = R.drawable.ic_delete),
              onClick = { onDeleteClick(moodTag) },
            ),
          ),
        )
      }
    }

    FloatingActionButton(
      onClick = onAddMoodTagClicked,
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

  state.moodTagToBeDeleted?.let { moodTagToBeDeleted ->
    MultipleActionDialog(
      title = "Are you sure?",
      summary = "You can archive a tag so it will still be visible in previous days or you can remove it completely so it is gone forever",
      onBackgroundClick = onCloseDialog,
      actionItems = listOf(
        DialogActionItem(
          text = "Archive",
          onClick = { onArchiveConfirm(moodTagToBeDeleted) },
        ),
        DialogActionItem(
          text = "Remove",
          onClick = { onDeleteConfirm(moodTagToBeDeleted) },
        ),
        DialogActionItem(
          text = "Cancel",
          onClick = onCloseDialog,
        ),
      ),
    )
  }
}
