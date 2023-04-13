package org.dandowney.bestpartofmyday.feature.today.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.theme.isDark
import org.dandowney.bestpartofmyday.design.theme.textDark
import org.dandowney.bestpartofmyday.design.theme.textLight
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.feature.tags.composable.MoodTagItem
import org.dandowney.bestpartofmyday.feature.today.contract.TodayScreenState

@Composable
internal fun Summarize(
  state: TodayScreenState.Summarize,
  onBackClicked: () -> Unit,
  onFinishClicked: () -> Unit,
) {
  val backgroundColor = Color(state.backgroundColor)
  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(color = backgroundColor),
  ) {
    val (appBar, date, bestPartLabel, bestPart, moodTagsLabel, moodTags, finish) = createRefs()

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
      text = state.date,
      style = MaterialTheme.typography.h3,
      color = contrastingColor,
      textAlign = TextAlign.Start,
      modifier = Modifier
        .constrainAs(date) {
          top.linkTo(appBar.bottom, margin = 16.dp)
          centerHorizontallyTo(parent)
        }
        .padding(horizontal = 16.dp),
    )

    Text(
      text = "Best part of my day",
      style = MaterialTheme.typography.subtitle1,
      color = contrastingColor,
      textAlign = TextAlign.Start,
      modifier = Modifier
        .fillMaxWidth()
        .constrainAs(bestPartLabel) {
          top.linkTo(date.bottom, margin = 64.dp)
          centerHorizontallyTo(parent)
        }
        .padding(horizontal = 16.dp),
    )

    Text(
      text = state.bestPart,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .constrainAs(bestPart) {
          top.linkTo(bestPartLabel.bottom, margin = 16.dp)
          centerHorizontallyTo(parent)
        }
        .padding(horizontal = 16.dp),
      color = contrastingColor,
      style = MaterialTheme.typography.h4,
      textAlign = TextAlign.Start,
    )

    Text(
      text = "Mood tags",
      style = MaterialTheme.typography.subtitle1,
      color = contrastingColor,
      textAlign = TextAlign.Start,
      modifier = Modifier
        .fillMaxWidth()
        .constrainAs(moodTagsLabel) {
          top.linkTo(bestPart.bottom, margin = 64.dp)
          centerHorizontallyTo(parent)
        }
        .padding(horizontal = 16.dp),
    )

//    FlowRow(
//      modifier = Modifier
//        .fillMaxWidth()
//        .constrainAs(moodTags) {
//          top.linkTo(moodTagsLabel.bottom, margin = 16.dp)
//          centerHorizontallyTo(parent)
//        }
//        .padding(horizontal = 16.dp),
//      maxItemsInEachRow = 4,
//      horizontalArrangement = spacedBy(16.dp, alignment = Alignment.Start),
////      verticalAlignment = spacedBy(16.dp, alignment = Alignment.Top),
//    ) {
//      state.selectedTags.forEach { tag ->
//        MoodTagItem(
//          item = tag,
//          onTagSelected = { },
//          isDark = backgroundColor.isDark(),
//          modifier = Modifier.weight(1F),
//        )
//      }
//    }

    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .constrainAs(moodTags) {
          top.linkTo(moodTagsLabel.bottom)
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
        bottom = 64.dp,
      ),
    ) {

      items(items = state.selectedTags) { selectedTag ->
        MoodTagItem(
          item = selectedTag,
          onTagClicked = { },
          isDarkTheme = backgroundColor.isDark(),
        )
      }
    }

    FloatingActionButton(
      onClick = onFinishClicked,
      modifier = Modifier
        .padding(32.dp)
        .size(64.dp)
        .constrainAs(finish) {
          bottom.linkTo(parent.bottom)
          end.linkTo(parent.end)
        },
      backgroundColor = contrastingColor,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_check),
        contentDescription = null,
        tint = backgroundColor,
        modifier = Modifier
          .size(24.dp),
      )
    }
  }
}
