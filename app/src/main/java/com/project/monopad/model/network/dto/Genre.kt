package com.project.monopad.model.network.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genre")
data class Genre(
    @PrimaryKey val id: Int,
    val name: String
)