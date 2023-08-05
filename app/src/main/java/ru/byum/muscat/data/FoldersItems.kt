package ru.byum.muscat.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("folder"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FoldersItems(
    @ColumnInfo(index = true)
    val folder: Int,
    val item: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    )