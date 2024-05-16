package com.example.unit_tests.domain.repository.task

import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task
import kotlinx.coroutines.flow.Flow

interface GroupsRepository {

    fun getAllGroups(): Flow<List<Group>>

    suspend fun addNewGroup(group: Group)

}