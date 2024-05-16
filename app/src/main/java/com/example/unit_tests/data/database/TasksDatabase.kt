package com.example.unit_tests.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unit_tests.data.database.entity.group.Group
import com.example.unit_tests.data.database.entity.task.Task

@Database(
    entities = [
        Task::class,
        Group::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun testsDao(): TasksDao

}