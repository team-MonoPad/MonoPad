package com.project.monopad.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Review")
data class Review(
    @PrimaryKey(autoGenerate = true) var id: Int = 0, // pk
    var review_poster: String,
    var title: String,
    var date: Date,
    var comment: String,
    var rating: Double,
    var movie: Movie
)