package org.dandowney.bestpartofmyday.feature.calendar.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.design.extensions.clickableNoIndication
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.theme.isDark
import org.dandowney.bestpartofmyday.design.ui.BestTopAppBar
import org.dandowney.bestpartofmyday.design.ui.PaletteGradientSurface
import org.dandowney.bestpartofmyday.feature.calendar.contract.CalenderScreenState
import org.dandowney.bestpartofmyday.feature.calendar.contract.MonthOverviewDayState
import java.time.DayOfWeek

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonthOverview(
  state: CalenderScreenState.MonthOverview,
  onNavigateBack: () -> Unit,
  onPreviousMonthClicked: () -> Unit,
  onNextMonthClicked: () -> Unit,
) {
  PaletteGradientSurface(
    colors = state.activeColorPalette,
    modifier = Modifier.fillMaxSize(),
  )
  Column(
    modifier = Modifier
      .fillMaxSize(),
    verticalArrangement = spacedBy(16.dp),
    horizontalAlignment = Alignment.Start,
  ) {

    BestTopAppBar(
      iconTint = Gray800,
      onBackClicked = onNavigateBack,
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 32.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_chevron_left),
        contentDescription = null,
        tint = Gray800,
        modifier = Modifier
          .size(24.dp)
          .clickableNoIndication(onClick = onPreviousMonthClicked),
      )
      Text(
        text = state.displayDate,
        style = MaterialTheme.typography.h3,
        modifier = Modifier
          .padding(horizontal = 16.dp)
      )
      Icon(
        painter = painterResource(id = R.drawable.ic_chevron_right),
        contentDescription = null,
        tint = Gray800,
        modifier = Modifier
          .size(24.dp)
          .clickableNoIndication(onClick = onNextMonthClicked),
      )
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      FlowRow(
        maxItemsInEachRow = 7,
        modifier = Modifier
          .wrapContentHeight()
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        enumValues<DayOfWeek>().forEach { day ->
          CalendarDayLabel(
            letter = day.name.first().toString(),
            modifier = Modifier.weight(1F),
          )
        }

        state.days.forEach { day ->
          when (day) {
            MonthOverviewDayState.Blank -> CalendarBlankItem(
              modifier = Modifier.weight(1F),
            )
            is MonthOverviewDayState.Populated -> CalendarDayItem(
              item = day,
              modifier = Modifier.weight(1F),
            )
          }
        }
      }
    }
  }
}

@Composable
fun CalendarBlankItem(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .padding(8.dp)
      .size(32.dp),
  )
}

@Composable
fun CalendarDayLabel(
  letter: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .padding(8.dp)
      .size(32.dp),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = letter,
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold,
    )
  }
}

@Composable
fun CalendarDayItem(
  item: MonthOverviewDayState.Populated,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .padding(8.dp)
      .size(32.dp),
    contentAlignment = Alignment.Center,
  ) {
    // TODO: Clean up this state model so this color logic can be easier to understand
    val daySummary = item.daySummary
    val dateColor = daySummary?.color?.let(::Color)
    val textColor = when {
      daySummary != null && daySummary.bestPart.isNotEmpty() -> Gray200
      else -> Gray800
    }
    val backgroundModifier = when {
      daySummary != null && daySummary.bestPart.isNotEmpty() -> {
        Modifier.background(
          color = dateColor ?: Color.Transparent,
          shape = RoundedCornerShape(8.dp),
        )
      }
      daySummary != null -> {
        Modifier.border(
          color = dateColor ?: Color.Transparent,
          shape = RoundedCornerShape(8.dp),
          width = 1.dp,
        )
      }
      else -> Modifier
    }

    Box(
      modifier = backgroundModifier
        .size(32.dp),
    )
    Text(
      text = item.dayNumber,
      textAlign = TextAlign.Center,
      color = when {
        dateColor != null && dateColor.isDark() -> textColor
        else -> Gray800
      }
    )
  }
}
