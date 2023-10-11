package com.andreisingeleytsev.ingetinapp.ui.screens.edit_event_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.ingetinapp.ui.utils.UIEvents
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import com.andreisingeleytsev.ingetinapp.data.repository.EventItemRepository
import com.andreisingeleytsev.ingetinapp.ui.utils.MonthConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EditEventScreenViewModel @Inject constructor(
    private val eventItemRepository: EventItemRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: EditEventScreenEvents){
        when(event){
            is EditEventScreenEvents.OnSave -> {
                event.observer.value = false
                if (title.value.isNotEmpty()&&name.value.isNotEmpty()) {
                    viewModelScope.launch {
                        eventItemRepository.insertItem(EventItem(
                            id = id,
                            name = name.value,
                            title = title.value,
                            day = day.value,
                            month = month.value,
                            year = year.value,
                            isPersonal = dropdownIndex.value != 0
                        ))
                    }
                    Log.d("tag", EventItem(
                        id = id,
                        name = name.value,
                        title = title.value,
                        day = day.value,
                        month = month.value,
                        year = year.value,
                        isPersonal = dropdownIndex.value != 0
                    ).toString())
                    sendUIEvent(UIEvents.OnBack)
                }
            }
        }
    }
    private var id: Int? = null
    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    val day = mutableStateOf(0)
    val month = mutableStateOf(0)
    val year = mutableStateOf(0)
    val name = mutableStateOf(
        ""
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val dayOfWeek = mutableStateOf(
        ""
    )
    val title = mutableStateOf(
        ""
    )

    val dropdownIndex = mutableStateOf(
        0
    )

    init {
        year.value = savedStateHandle.get<String>("year")?.toInt() ?: -1
        month.value = MonthConverter.fromMonthToInt(savedStateHandle.get<String>("month")!!)
        day.value = savedStateHandle.get<String>("day")?.toInt() ?: -1
        val _id = savedStateHandle.get<String>("event_id")?.toInt()
        if (_id!=-1) id = _id
        dayOfWeek.value = LocalDate.of(year.value, month.value+1, day.value).dayOfWeek.name
        if (id != null) {
            viewModelScope.launch {
                eventItemRepository.getEventByID(id!!).let { note_item ->
                    name.value = note_item.name
                    title.value = note_item.title
                    year.value = note_item.year
                    month.value = note_item.month
                    day.value = note_item.day
                    dropdownIndex.value = if (note_item.isPersonal) 1
                    else 0
                }
            }
        }
    }

}