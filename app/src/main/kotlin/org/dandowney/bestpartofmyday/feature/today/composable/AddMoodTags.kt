package org.dandowney.bestpartofmyday.feature.today.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.theme.isDark
import org.dandowney.bestpartofmyday.design.theme.textDark
import org.dandowney.bestpartofmyday.design.theme.textLight
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.design.ui.button.FilledButton
import org.dandowney.bestpartofmyday.feature.tags.composable.MoodTagItem
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.feature.today.contract.TodayScreenState

@Composable
fun AddMoodTags(
  state: TodayScreenState.AddMoodTags,
  onTagSelected: (MoodTagState) -> Unit,
  onTagsSaved: () -> Unit,
  onBackClicked: () -> Unit,
  onToggleArchivedTags: () -> Unit,
) {

  val backgroundColor = Color(state.backgroundColor)
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(color = backgroundColor),
  ) {

    val (appBar, question, tags, save) = createRefs()
    val contrastingColor = if (backgroundColor.isDark()) textDark else textLight

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
      text = "What moods did you feel today?",
      style = MaterialTheme.typography.h2,
      color = contrastingColor,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .constrainAs(question) {
          top.linkTo(appBar.bottom, margin = 16.dp)
          centerHorizontallyTo(parent)
        },
    )

    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .constrainAs(tags) {
          top.linkTo(question.bottom)
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
          height = Dimension.fillToConstraints
        },
      horizontalArrangement = spacedBy(16.dp),
      verticalArrangement = spacedBy(16.dp),
      contentPadding = PaddingValues(
        start = 16.dp,
        top = 32.dp,
        end = 16.dp,
        bottom = 128.dp,
      ),
    ) {

      items(items = state.possibleTags) { tag ->
        MoodTagItem(
          item = tag,
          isDarkTheme = backgroundColor.isDark(),
          onTagClicked = onTagSelected,
        )
      }

      item(
        span = { GridItemSpan(3) },
      ) {
        FilledButton(
          text = if (state.shouldShowArchived) "Hide archived tags" else "Show archived tags",
          onClick = onToggleArchivedTags,
          modifier = Modifier
            .padding(vertical = 16.dp),
        )
      }

      if (state.shouldShowArchived) {
        items(items = state.archivedTags) { archivedTag ->
          MoodTagItem(
            item = archivedTag,
            isDarkTheme = backgroundColor.isDark(),
            onTagClicked = onTagSelected,
          )
        }
      }
    }

    FloatingActionButton(
      onClick = onTagsSaved,
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
}
