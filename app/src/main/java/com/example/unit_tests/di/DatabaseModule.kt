package com.example.unit_tests.di

import android.content.Context
import androidx.room.Room
import com.example.unit_tests.data.database.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        TasksDatabase::class.java,
        "pnc"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: TasksDatabase) = db.testsDao()
}

