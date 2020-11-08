package com.project.monopad.data.model.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Genre")
data class Genre(
    @PrimaryKey val id: Int,
    val name: String?
) : Parcelable