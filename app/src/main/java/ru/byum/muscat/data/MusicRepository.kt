package ru.byum.muscat.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import ru.byum.muscat.data.local.database.MusicDao
import ru.byum.muscat.data.local.database.Rating
import ru.byum.muscat.data.network.DiscogsAPI
import javax.inject.Inject

interface MusicRepository {
    suspend fun searchReleases(query: String): ReleaseSearchResults?
    suspend fun searchArtists(query: String): ArtistsSearchResults?
    suspend fun getReleases(id: Int?): ArtistReleases?
    suspend fun setRating(id: String, rating: Int)
    suspend fun getRating(id: String): Int
    val folders: Flow<List<Folder>>
    suspend fun createFolder(title: String, type: FolderType)
    suspend fun deleteFolder(id: Int)
    suspend fun getFolderItems(id: Int): List<String>
    suspend fun getFolderReleases(id: Int): List<Release>

    suspend fun getFolderArtists(id: Int) : List<Artist>
    suspend fun addItemToFolder(folder: Int, item: String)
}

class DefaultMusicRepository @Inject constructor(
    private val musicDao: MusicDao,
    private val discogs: DiscogsAPI
) : MusicRepository {
    override suspend fun searchReleases(query: String): ReleaseSearchResults? {
        return discogs.searchReleases(query)
    }

    override suspend fun searchArtists(query: String): ArtistsSearchResults? {
        return discogs.searchArtists(query)
    }

    override suspend fun getReleases(id: Int?): ArtistReleases? {
        return discogs.getArtistReleases(id)
    }

    override suspend fun setRating(id: String, rating: Int) {
        musicDao.setRating(Rating(id.toInt(), rating))
    }

    override suspend fun getRating(id: String): Int {
        return musicDao.getRating(id)?.rating ?: 0
    }

    override val folders: Flow<List<Folder>> = musicDao.getAllFolders()

    override suspend fun createFolder(title: String, type: FolderType) {
        musicDao.createFolder(Folder(title, type))
    }

    override suspend fun deleteFolder(id: Int) {
        musicDao.deleteFolder(id)
    }

    override suspend fun getFolderItems(id: Int): List<String> {
        return musicDao.getFolderItems(id).map { it.item }
    }

    override suspend fun getFolderReleases(id: Int): List<Release> {

        return musicDao.getFolderItems(id)
            .mapNotNull { discogs.getRelease(it.item.toInt()) }
            .map { it.toRelease() }
    }

    override suspend fun getFolderArtists(id: Int): List<Artist> {
        return musicDao.getFolderItems(id)
            .mapNotNull { discogs.getArtist(it.item.toInt()) }
            .map { it.toArtist() }
    }

    override suspend fun addItemToFolder(folder: Int, item: String) {
        musicDao.addItemToFolder(FoldersItems(folder, item))
        Log.d("MusicRepository", "ID: ${item}")
    }
}
