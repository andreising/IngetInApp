package com.andreisingeleytsev.ingetinapp.data.repository

import com.andreisingeleytsev.ingetinapp.data.dao.EventItemDao
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import kotlinx.coroutines.flow.Flow

interface EventItemRepository {
    suspend fun insertItem(item: EventItem)
    suspend fun deleteItem(item: EventItem)
    fun getItems(): Flow<List<EventItem>>
    fun getGeneralItems(): Flow<List<EventItem>>
    fun getPersonalItems(): Flow<List<EventItem>>
    suspend fun getEventByID(event_id:Int): EventItem
    fun getEventByDay(year: Int, month: Int, day: Int): Flow<List<EventItem>>
    suspend fun getEventByMonth(year: Int, month: Int): List<EventItem>
}