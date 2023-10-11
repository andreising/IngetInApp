package com.andreisingeleytsev.ingetinapp.ui.screens.calendar_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.ingetinapp.ui.utils.UIEvents
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import com.andreisingeleytsev.ingetinapp.data.repository.EventItemRepository
import com.andreisingeleytsev.ingetinapp.domain.provider.CurrentDateProvider
import com.andreisingeleytsev.ingetinapp.ui.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val eventItemRepository: EventItemRepository,
    private val currentDateProvider: CurrentDateProvider
): ViewModel() {

    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CalendarScreenEvent){
        when(event){
            is CalendarScreenEvent.OnDayChose -> {
                currentDateProvider.insertDate(event.day)
                sendUIEvent(UIEvents.OnNavigate(Routes.HOME_SCREEN))
            }
        }
    }

    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


    val list = mutableStateOf(emptyList<EventItem>())
    val currentDate = mutableStateOf(
        currentDateProvider.getDate()
    )

    init {
        viewModelScope.launch {
            list.value = eventItemRepository.getEventByMonth(
                currentDate.value.year,
                currentDate.value.month.value-1
            )
        }
    }
}