package com.project.monopad.model.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.monopad.model.network.dto.Genre
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey var id: Int,
    var title: String?,
    var overview: String?,
    var release_date: String?,
    var genres: List<Genre>?
) : Parcelable