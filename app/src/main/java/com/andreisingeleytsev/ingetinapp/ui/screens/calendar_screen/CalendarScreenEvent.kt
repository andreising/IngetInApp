package com.andreisingeleytsev.ingetinapp.ui.screens.calendar_screen

import java.time.LocalDate

sealed class CalendarScreenEvent{
    data class OnDayChose(val day: LocalDate): CalendarScreenEvent()
}
