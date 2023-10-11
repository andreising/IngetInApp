package com.andrei_singeleytsev.sportquizapp.data.room.repository.implementations

import com.andreisingeleytsev.ingetinapp.data.repository.EventItemRepository
import com.andreisingeleytsev.ingetinapp.data.dao.EventItemDao
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import kotlinx.coroutines.flow.Flow

class EventItemRepositoryImpl(
    private val dao: EventItemDao
) : EventItemRepository {
    override suspend fun insertItem(item: EventItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: EventItem) {
        dao.deleteItem(item)
    }

    override fun getItems(): Flow<List<EventItem>> {
        return dao.getItems()
    }

    override fun getGeneralItems(): Flow<List<EventItem>> {
        return getGeneralItems()
    }

    override fun getPersonalItems(): Flow<List<EventItem>> {
        return dao.getPersonalItems()
    }

    override suspend fun getEventByID(event_id: Int): EventItem {
        return dao.getEventByID(event_id)
    }

    override fun getEventByDay(year: Int, month: Int, day: Int): Flow<List<EventItem>> {
        return dao.getEventByDay(year, month, day)
    }

    override suspend fun getEventByMonth(year: Int, month: Int): List<EventItem> {
        return dao.getEventByMonth(year, month)
    }

}