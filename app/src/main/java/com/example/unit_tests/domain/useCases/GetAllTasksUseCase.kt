package com.example.unit_tests.domain.useCases

import com.example.unit_tests.domain.repository.task.TasksRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {

    operator fun invoke() = taskRepository.getAllTasks()
}