package ru.byum.muscat.data

import android.util.Log
import ru.byum.muscat.data.local.database.MusicDao
import ru.byum.muscat.data.local.database.Rating
import ru.byum.muscat.data.network.DiscogsAPI
import javax.inject.Inject

const val TAG = "Music Repo"

interface MusicRepository {
    suspend fun searchReleases(query: String): ReleaseSearchResults?
    suspend fun searchArtists(query: String): ArtistsSearchResults?
    suspend fun getReleases(id: Int?): ArtistReleases?
    suspend fun setRating(id: String, rating: Int)
    suspend fun getRating(id: String): Int
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
        Log.d(TAG, "!!! ${id} ${rating}")
        musicDao.setRating(Rating(id.toInt(), rating))
    }

    override suspend fun getRating(id: String): Int {
        val rating =  musicDao.getRating(id)
        Log.d(TAG, "!!! ${rating}")
        return rating?.rating ?: 0
    }
}
