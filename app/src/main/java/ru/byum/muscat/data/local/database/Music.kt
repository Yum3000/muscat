package ru.byum.muscat.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FoldersContent

@Dao
interface MusicDao {
    @Query("SELECT * FROM Rating WHERE itemId=:id")
    fun getRating(id: String): Rating?

    @Upsert(entity = Rating::class)
    fun setRating(rating: Rating)

    @Query("SELECT * FROM Folder")
    fun getAllFolders(): Flow<List<Folder>>

    @Insert(entity = Folder::class)
    fun createFolder(folder: Folder)

    @Query("DELETE FROM Folder WHERE id = :id")
    fun deleteFolder(id: Int)


    //@Insert(entity = FoldersContent::class)
    //fun addContentIntoFolder(idFolder:Int, content: String)

    //@Query("DELETE FROM FoldersContent WHERE id = :id")
    //fun deleteContentFromFolder(id: Int)

    //@Query("SELECT * FROM FoldersContent WHERE id_folder=:idFolder")
    //fun getContentFromFolder(idFolder:Int): String




}
