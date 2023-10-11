package com.andreisingeleytsev.ingetinapp.ui.screens.home_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import com.andreisingeleytsev.ingetinapp.ui.utils.UIEvents
import com.andreisingeleytsev.ingetinapp.data.repository.EventItemRepository
import com.andreisingeleytsev.ingetinapp.domain.provider.CurrentDateProvider
import com.andreisingeleytsev.ingetinapp.ui.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val dateProvider: CurrentDateProvider,
    private val eventItemRepository: EventItemRepository
): ViewModel(){

    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnNavigateToEdit -> {
                sendUIEvent(UIEvents.OnNavigate(Routes.EDIT_EVENT_SCREEN + "/${choseDate.value.year}" + "/${choseDate.value.month}" + "/${choseDate.value.dayOfMonth}/-1"))
                event.observer.value = false
            }
            is HomeScreenEvent.OnWeekChoose -> {
                if (dropdownIndex.value!=event.index) {
                    if (event.index==0) {
                        sendUIEvent(UIEvents.OnChangeWeek(-1))
                        choseDate.value = choseDate.value.minusWeeks(1)
                    } else {
                        sendUIEvent(UIEvents.OnChangeWeek(1))
                        choseDate.value = choseDate.value.plusWeeks(1)
                    }
                    dropdownIndex.value = event.index
                }
            }
            is HomeScreenEvent.OnDayChoose -> {
                choseDate.value = event.day
                viewModelScope.launch {
                    list = eventItemRepository.getEventByDay(
                        choseDate.value.year,
                        choseDate.value.month.value-1,
                        choseDate.value.dayOfMonth,
                    )
                }
                viewModelScope.launch {
                    monthList.value = eventItemRepository.getEventByMonth(
                        choseDate.value.year,
                        choseDate.value.month.value-1
                    )
                }
            }
            is HomeScreenEvent.OnDeleteEvent -> {
                viewModelScope.launch {
                    eventItemRepository.deleteItem(event.eventItem)
                }
            }
            is HomeScreenEvent.OnEditEvent -> {
                val item = event.eventItem
                sendUIEvent(UIEvents.OnNavigate(Routes.EDIT_EVENT_SCREEN + "/${item.year}" + "/${item.month}" + "/${item.day}"+ "/${item.id}"))
            }
        }
    }

    private fun sendUIEvent(event: UIEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }



    val choseDate = mutableStateOf(
        dateProvider.getDate()
    )
    val dropdownIndex = mutableStateOf(
        0
    )

    var tabState by  mutableIntStateOf(0)

    @RequiresApi(Build.VERSION_CODES.O)
    var list = eventItemRepository.getEventByDay(
        choseDate.value.year,
        choseDate.value.month.value - 1,
        choseDate.value.dayOfMonth,
    )

    val monthList = mutableStateOf(emptyList<EventItem>())

    init {
        sendUIEvent(UIEvents.OnChangeWeek((choseDate.value.dayOfYear - LocalDate.now().dayOfYear) / 7))
        viewModelScope.launch {
            monthList.value = eventItemRepository.getEventByMonth(
                choseDate.value.year,
                choseDate.value.month.value - 1
            )
        }
    }

}