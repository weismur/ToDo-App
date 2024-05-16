package com.example.unit_tests.data.repository.group

import com.example.unit_tests.data.database.TestsDao
import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task
import com.example.unit_tests.domain.repository.task.GroupsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(private val dao: TestsDao): GroupsRepository {
    override fun getAllGroups(): Flow<List<Group>> {
        return dao.getGroups()
    }

    override suspend fun addNewGroup(group: Group) {
        dao.addNewGroup(group)
    }


}