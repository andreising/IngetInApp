package com.andreisingeleytsev.ingetinapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import kotlinx.coroutines.flow.Flow
import java.time.Year

@Dao
interface EventItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: EventItem)
    @Delete
    suspend fun deleteItem(item: EventItem)
    @Query("SELECT * FROM event_item")
    fun getItems(): Flow<List<EventItem>>
    @Query("SELECT * FROM event_item WHERE isPersonal = 0")
    fun getGeneralItems(): Flow<List<EventItem>>
    @Query("SELECT * FROM event_item WHERE isPersonal = 1")
    fun getPersonalItems(): Flow<List<EventItem>>
    @Query("SELECT * FROM event_item WHERE id IS :event_id")
    suspend fun getEventByID(event_id:Int): EventItem
    @Query("SELECT * FROM event_item WHERE year = :year AND month = :month AND day =:day")
    fun getEventByDay(year: Int, month: Int, day: Int): Flow<List<EventItem>>
    @Query("SELECT * FROM event_item WHERE year = :year AND month = :month")
    suspend fun getEventByMonth(year: Int, month: Int): List<EventItem>
}