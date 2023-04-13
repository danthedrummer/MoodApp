package org.dandowney.bestpartofmyday.feature.calendar.contract

import org.dandowney.bestpartofmyday.base.ViewEvent

sealed interface CalendarEvent : ViewEvent {

  object PreviousMonthClicked : CalendarEvent

  object NextMonthClicked : CalendarEvent
}
