package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.task.Task
import com.example.unit_tests.domain.repository.task.TasksRepository
import javax.inject.Inject

class changeTaskStatusUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {

    suspend operator fun invoke(task: Task) = taskRepository.changeTaskStatus(task)
}