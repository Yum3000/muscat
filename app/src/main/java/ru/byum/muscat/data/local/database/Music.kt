package ru.byum.muscat.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Music(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface MusicDao {
    @Query("SELECT * FROM music ORDER BY uid DESC LIMIT 10")
    fun getMusics(): Flow<List<Music>>

    @Insert
    suspend fun insertMusic(item: Music)
}
