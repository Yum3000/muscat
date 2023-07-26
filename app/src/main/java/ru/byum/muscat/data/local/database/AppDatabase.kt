package ru.byum.muscat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FoldersContent

@Database(entities = [
    Rating::class,
    Folder::class,
    FoldersContent::class
                     ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
