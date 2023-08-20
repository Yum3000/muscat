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
    suspend fun getArtistReleases(id: Int?): List<Release>
    suspend fun getRelease(id: Int): Release?
    suspend fun setRating(id: Int, rating: Int)

    //    suspend fun getRating(id: Int): Int
    val folders: Flow<List<Folder>>
    suspend fun createFolder(title: String, type: FolderType)
    suspend fun deleteFolder(id: Int)
    suspend fun getFolderItems(id: Int): List<String>
    suspend fun getFolderReleases(id: Int): List<Release>

    suspend fun getFolderArtists(id: Int): List<Artist>
    suspend fun addItemToFolder(folder: Int, item: String)

//    suspend fun getRatings(items: List<Int>): List<Rating>
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

    override suspend fun getArtistReleases(id: Int?): List<Release> {
        val artistReleases = discogs.getArtistReleases(id)
        val allRatings = musicDao.getAllRatings()

        return artistReleases?.releases
            ?.map { artistRelease ->
                val release = artistRelease.toRelease()
                release.rating = allRatings.find { it.itemId == release.id }?.rating ?: 0
                release
            } ?: listOf()
    }

    override suspend fun getRelease(id: Int): Release? {
        val release = discogs.getRelease(id)
        return release?.toRelease()
    }

    override suspend fun setRating(id: Int, rating: Int) {
        musicDao.setRating(Rating(id, rating))
    }

//    override suspend fun getRating(id: Int): Int {
//        return musicDao.getRating(id)?.rating ?: 0
//    }

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

//    override suspend fun getRatings(items: List<Int>): List<Rating> {
//        val ratings = items.mapNotNull { musicDao.getRating(it) }
//
////        val acc = mutableMapOf<Int, Int>()
////        for ()
////        ratings.forEach { rating -> acc[rating.itemId] = rating.rating }
//
//        return ratings
//    }
}
