package ru.byum.muscat.data

import kotlinx.coroutines.flow.Flow
import ru.byum.muscat.data.local.database.MusicDao
import ru.byum.muscat.data.local.database.Rating
import ru.byum.muscat.data.network.NetworkArtistsSearchResults
import ru.byum.muscat.data.network.DiscogsAPI
import ru.byum.muscat.data.network.NetworkReleaseSearchResults
import javax.inject.Inject

interface MusicRepository {
    suspend fun searchReleases(query: String): NetworkReleaseSearchResults?
    suspend fun searchArtists(query: String): NetworkArtistsSearchResults?
    suspend fun getArtistReleases(id: Int): List<Release>
    suspend fun getRelease(id: Int): Release?
    suspend fun setRating(id: Int, rating: Int, genre: String = "")
    suspend fun setReleaseRating(id: Int, rating: Int)


    val folders: Flow<List<Folder>>
    suspend fun createFolder(title: String, type: FolderType)
    suspend fun deleteFolder(id: Int)

    suspend fun removeItemFromFolder(folder: Int, itemID: Int)
    suspend fun getFolderItems(id: Int): List<String>
    suspend fun getFolderReleases(id: Int): List<Release>

    suspend fun getFolderArtists(id: Int): List<Artist>
    suspend fun addItemToFolder(folder: Int, item: String)

    suspend fun getArtist(id: Int): Artist?

    suspend fun getFoldersWithItem(itemID: Int): List<Int>
    suspend fun toggleItemInFolder(folder: Int, item: Int)

    suspend fun getRatingStats(): RatingStats
    suspend fun getFoldersStats(): FoldersStats
}

data class FoldersStats(
    val count: Int,
    val biggest: Pair<String, Int>,
    val smallest: Pair<String, Int>,
    val artistCount: Int,
    val releaseCount: Int,
    val itemsCount: Int,
)

data class RatingStats(
    val genresStat: Map<String, Int>,
    val counts: IntArray,
)

class DefaultMusicRepository @Inject constructor(
    private val musicDao: MusicDao,
    private val discogs: DiscogsAPI
) : MusicRepository {
    override suspend fun searchReleases(query: String): NetworkReleaseSearchResults? {
        return discogs.searchReleases(query)
    }

    override suspend fun searchArtists(query: String): NetworkArtistsSearchResults? {
        return discogs.searchArtists(query)
    }

    override suspend fun getRatingStats(): RatingStats {
        val counts = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val genreStat = mutableMapOf<String, Int>()
        val ratings = musicDao.getAllRatings()
        ratings.forEach {
            if (it.genre.isNotEmpty()) {
                genreStat[it.genre] = genreStat.getOrElse(it.genre, { 0 }) + 1
            }
            counts[it.rating - 1] += 1
        }

        return RatingStats(genreStat, counts)
    }

    override suspend fun getFoldersStats(): FoldersStats {
        val folders = musicDao.getAllFoldersNow()
        val foldersCounts = mutableMapOf<String, Int>()
        var maxFolderTitle = ""
        var maxFolderCount = Int.MIN_VALUE
        var minFolderTitle = ""
        var minFolderCount = Int.MAX_VALUE
        var releaseFolderCount = 0
        var artistFolderCount = 0
        var itemsCount = 0

        folders.forEach {
            val count = musicDao.getFolderItems(it.id).size

            itemsCount += count

            if (count > maxFolderCount) {
                maxFolderCount = count
                maxFolderTitle = it.title
            }
            if (count < minFolderCount) {
                minFolderCount = count
                minFolderTitle = it.title
            }

            if (it.type == FolderType.RELEASES) releaseFolderCount += 1
            if (it.type == FolderType.ARTIST) artistFolderCount += 1

            foldersCounts[it.title] = count
        }

        return FoldersStats(
            count = folders.size,
            Pair(maxFolderTitle, maxFolderCount),
            Pair(minFolderTitle, minFolderCount),
            artistFolderCount,
            releaseFolderCount,
            itemsCount
        )
    }

    override suspend fun getArtistReleases(id: Int): List<Release> {
        val artistReleases = discogs.getArtistReleases(id)
        val allRatings = musicDao.getAllRatings()

        return artistReleases?.releases.orEmpty()
            .filter { it.type == "master" }
            .map { artistRelease ->
                val release = artistRelease.toRelease()
                release.rating = allRatings.find { it.itemId == release.id }?.rating ?: 0
                release
            }
    }

    override suspend fun getRelease(id: Int): Release? {
        val release = discogs.getRelease(id)?.toRelease()
        val allRatings = musicDao.getAllRatings()

        release?.rating = allRatings.find { it.itemId == release?.id }?.rating ?: 0
        return release

    }

    override suspend fun setRating(id: Int, rating: Int, genre: String) {
        musicDao.setRating(Rating(id, rating, genre))
    }

    override suspend fun setReleaseRating(id: Int, rating: Int) {
        val release = discogs.getRelease(id)?.toRelease()
        musicDao.setRating(Rating(id, rating, release?.genre ?: ""))
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
        val folderReleases = musicDao.getFolderItems(id)
        val allRatings = musicDao.getAllRatings()

        return folderReleases
            .mapNotNull { discogs.getRelease(it.item.toInt()) }
            .map { networkRelease ->
                val release = networkRelease.toRelease()
                release.rating = allRatings.find { it.itemId == release.id }?.rating ?: 0
                release
            }
    }

    override suspend fun getFolderArtists(id: Int): List<Artist> {
        return musicDao.getFolderItems(id)
            .mapNotNull { discogs.getArtist(it.item.toInt()) }
            .map { it.toArtist() }
    }

    override suspend fun addItemToFolder(folder: Int, item: String) {
        musicDao.addItemToFolder(FoldersItems(folder, item))
    }

    override suspend fun toggleItemInFolder(folder: Int, item: Int) {
        val alreadyInFolder = musicDao.isItemInFolder(folder, item)

        if (!alreadyInFolder) {
            musicDao.addItemToFolder(FoldersItems(folder, item.toString()))
        } else {
            musicDao.removeItemFromFolder(folder, item)
        }
    }

    override suspend fun getArtist(id: Int): Artist? {

        val artist = discogs.getArtist(id)?.toArtist()
        val allRatings = musicDao.getAllRatings()

        artist?.rating = allRatings.find { it.itemId == artist?.id }?.rating ?: 0

        return artist
    }

    override suspend fun getFoldersWithItem(itemID: Int): List<Int> {
        val checking = musicDao.getFoldersItemsByItem(itemID)
        return checking.map { it.folder }
    }

    override suspend fun removeItemFromFolder(folder: Int, itemID: Int) {
        musicDao.removeItemFromFolder(folder, itemID)
    }

}
