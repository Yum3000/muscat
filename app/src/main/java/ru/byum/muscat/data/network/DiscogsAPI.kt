package ru.byum.muscat.data.network

import retrofit2.Response
import retrofit2.http.Path
import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResults

interface DiscogsAPI {
    suspend fun searchReleases(query:String) : ReleaseSearchResults?

    suspend fun searchArtists(query:String) : ArtistsSearchResults?

    suspend fun getArtistReleases(id: Int?): ArtistReleases?

    suspend fun getRelease(releaseID: Int): ReleaseNetwork?

    suspend fun getArtist(artistID: Int) : ArtistNetwork?
}
