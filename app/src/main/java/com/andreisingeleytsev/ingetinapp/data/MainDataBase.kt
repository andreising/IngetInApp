package com.andrei_singeleytsev.sportquizapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreisingeleytsev.ingetinapp.data.dao.EventItemDao
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem

@Database(
    entities = [EventItem::class],
    version = 1
)
abstract class MainDataBase: RoomDatabase() {
    abstract val noteItemDao: EventItemDao
}