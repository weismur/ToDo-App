package com.example.unit_tests.di.repository

import com.example.unit_tests.data.repository.task.TasksRepositoryImpl
import com.example.unit_tests.domain.repository.task.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTasksRepository(repositoryImpl: TasksRepositoryImpl): TasksRepository
}