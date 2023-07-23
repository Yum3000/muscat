package ru.byum.muscat.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MusicDao {
    @Query("SELECT * FROM Rating WHERE itemId=:id")
    fun getRating(id: String): Rating?

    @Upsert(entity = Rating::class)
    fun setRating(rating: Rating)
}
