package ru.byum.muscat.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FoldersItems

@Dao
interface MusicDao {
    // Выбрать набор записей по их itemId почему-то не получается. Поэтому всегда выбираем всё.
    @Query("SELECT * FROM Rating")
    fun getAllRatings(): List<Rating>

    @Upsert(entity = Rating::class)
    fun setRating(rating: Rating)

    @Query("SELECT * FROM Folder")
    fun getAllFolders(): Flow<List<Folder>>

    @Insert(entity = Folder::class)
    fun createFolder(folder: Folder)

    @Query("DELETE FROM Folder WHERE id = :id")
    fun deleteFolder(id: Int)

    @Insert(entity = FoldersItems::class)
    fun addItemToFolder(folder: FoldersItems)

    @Query("DELETE FROM FoldersItems WHERE folder = :folder AND item = :item")
    fun removeItemFromFolder(folder: Int, item: Int)

    @Query("SELECT * FROM FoldersItems WHERE folder = :folder")
    fun getFolderItems(folder: Int): List<FoldersItems>

    @Query("SELECT * FROM FoldersItems WHERE item = :item")
    fun getFoldersItemsByItem(item: Int): List<FoldersItems>

    @Query("SELECT EXISTS(SELECT * FROM FoldersItems WHERE folder = :folder AND item = :item)")
    fun isItemInFolder(folder: Int, item: Int): Boolean

}
