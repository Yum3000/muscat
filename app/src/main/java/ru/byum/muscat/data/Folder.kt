package ru.byum.muscat.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

enum class FolderType {
    ARTIST, RELEASES
}

@Entity()
data class Folder(
    val title: String,
    val type: FolderType,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

