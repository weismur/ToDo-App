package com.example.unit_tests.data.repository.task

import com.example.unit_tests.data.database.TestsDao
import com.example.unit_tests.data.database.entity.task.Task
import com.example.unit_tests.domain.repository.task.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(private val dao: TestsDao): TasksRepository {
    override fun getAllTasksUseCase(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun addNewTask(task: Task) {
        dao.addNewTask(task)
    }

    override suspend fun changeTaskStatus(task: Task) {
        dao.changeTaskStatus(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

}