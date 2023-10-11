package com.andreisingeleytsev.ingetinapp.ui.screens.home_screen

import androidx.compose.runtime.MutableState
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import java.time.LocalDate

sealed class HomeScreenEvent{
    data class OnNavigateToEdit(val observer: MutableState<Boolean>): HomeScreenEvent()
    data class OnWeekChoose(val index: Int): HomeScreenEvent()
    data class OnEditEvent(val eventItem: EventItem): HomeScreenEvent()
    data class OnDeleteEvent(val eventItem: EventItem): HomeScreenEvent()
    data class OnDayChoose(val day: LocalDate): HomeScreenEvent()
}
