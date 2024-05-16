package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task
import com.example.unit_tests.domain.repository.task.GroupsRepository
import com.example.unit_tests.domain.repository.task.TasksRepository
import javax.inject.Inject

class AddNewGroupUseCase @Inject constructor(
    private val groupsRepository: GroupsRepository
) {

    suspend operator fun invoke(group: Group) = groupsRepository.addNewGroup(group)
}