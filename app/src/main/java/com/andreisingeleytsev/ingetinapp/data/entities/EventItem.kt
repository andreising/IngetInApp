package com.andreisingeleytsev.ingetinapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_item")
data class EventItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val title: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val isPersonal: Boolean
)
