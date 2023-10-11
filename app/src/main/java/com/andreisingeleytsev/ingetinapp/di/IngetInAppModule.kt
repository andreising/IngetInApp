package com.andreisingeleytsev.ingetinapp.di

import android.app.Application
import androidx.room.Room
import com.andrei_singeleytsev.sportquizapp.data.room.MainDataBase
import com.andreisingeleytsev.ingetinapp.data.repository.EventItemRepository
import com.andrei_singeleytsev.sportquizapp.data.room.repository.implementations.EventItemRepositoryImpl
import com.andreisingeleytsev.ingetinapp.domain.implementation.CurrentDateProviderImpl
import com.andreisingeleytsev.ingetinapp.domain.provider.CurrentDateProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IngetInAppModule{
    @Provides
    @Singleton
    fun provideMainDatabase(app: Application): MainDataBase {
        return Room.databaseBuilder(
            app,
            MainDataBase::class.java,
            "event_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideEventItemRepository(dataBase: MainDataBase): EventItemRepository {
        return EventItemRepositoryImpl(dataBase.noteItemDao)
    }
    @Provides
    @Singleton
    fun provideAddCurrentDate(): CurrentDateProvider {
        return CurrentDateProviderImpl()
    }
}

