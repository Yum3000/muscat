package ru.byum.muscat.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "FoldersContent",
    foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = ["id"],
            childColumns = ["idFolder"]
        )
    ]
)
data class FoldersContent(
    //@ColumnInfo(index = true)
    val idFolder: Int,
    val idInstance: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)