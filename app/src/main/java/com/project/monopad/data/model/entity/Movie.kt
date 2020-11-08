package com.project.monopad.data.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.monopad.data.model.dto.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey var id: Int,
    var title: String?,
    var overview: String?,
    var release_date: String?,
    var genres: List<Genre>?
) : Parcelable