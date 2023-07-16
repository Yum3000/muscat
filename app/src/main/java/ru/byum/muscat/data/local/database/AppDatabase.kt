package ru.byum.muscat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Music::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
