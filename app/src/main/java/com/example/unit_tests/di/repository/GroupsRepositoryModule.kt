package com.example.unit_tests.di.repository

import com.example.unit_tests.data.repository.group.GroupsRepositoryImpl
import com.example.unit_tests.domain.repository.task.GroupsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GroupsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGroupRepository(repositoryImpl: GroupsRepositoryImpl): GroupsRepository
}