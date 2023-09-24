package ru.byum.muscat.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rating(
    @PrimaryKey
    val itemId: Int,
    val rating: Int,
    val genre: String,
)
