package com.example.unit_tests.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.unit_tests.data.database.entity.task.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TestsDao {
    @Query("SELECT * FROM Task")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTask(task: Task)

    @Update
    suspend fun changeTaskStatus(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}