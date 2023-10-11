package com.andreisingeleytsev.ingetinapp.ui.screens.edit_event_screen

import androidx.compose.runtime.MutableState
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem

sealed class EditEventScreenEvents{
    data class OnSave(
        val observer: MutableState<Boolean>
    ): EditEventScreenEvents()
}
