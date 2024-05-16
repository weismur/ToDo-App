package com.example.unit_tests.data.database.entity.task

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    @NonNull val idTask: Int = 0,
    @NonNull val taskName: String,
    @NonNull val taskDescription: String,
    @NonNull val taskStatus: Int = 0,
    val groupNumber: Int? = null
)
