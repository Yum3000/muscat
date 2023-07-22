package ru.byum.muscat.data

import ru.byum.muscat.data.local.database.MusicDao
import ru.byum.muscat.data.network.DiscogsAPI
import javax.inject.Inject

interface MusicRepository {
    suspend fun searchReleases(query:String) : ReleaseSearchResults?
    suspend fun searchArtists(query:String) : ArtistsSearchResults?
    suspend fun getReleases(id:Int?): ArtistReleases?
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
}
