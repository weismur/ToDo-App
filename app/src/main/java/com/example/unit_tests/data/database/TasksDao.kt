package com.example.unit_tests.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM Task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM `Group`")
    fun getGroups(): Flow<List<Group>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewGroup(group: Group)

    @Update
    suspend fun changeTaskStatus(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}