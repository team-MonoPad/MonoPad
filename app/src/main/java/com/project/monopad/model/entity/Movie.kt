package com.project.monopad.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.monopad.model.network.dto.Genre

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey var id : Int, // pk
    var poster : String,
    var title : String,
    var overview : String,
    var release_date : String,
    var genres : List<Genre>
)