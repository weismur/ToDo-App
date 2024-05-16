package com.example.unit_tests.data.database.entity.group

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true)
    @NonNull val idGroup: Int = 0,
    @NonNull val groupName: String
)
