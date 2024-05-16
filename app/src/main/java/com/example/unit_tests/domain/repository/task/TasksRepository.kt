package com.example.unit_tests.domain.repository.task

import com.example.unit_tests.data.database.entity.task.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun addNewTask(task: Task)

    suspend fun changeTaskStatus(task: Task)

    suspend fun deleteTask(task: Task)
}