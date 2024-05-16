package com.example.unit_tests.domain.useCases

import com.example.unit_tests.domain.repository.task.GroupsRepository
import com.example.unit_tests.domain.repository.task.TasksRepository
import javax.inject.Inject

class GetAllGroupsUseCase @Inject constructor(
    private val groupsRepository: GroupsRepository
) {

    operator fun invoke() = groupsRepository.getAllGroups()
}