package ru.byum.muscat.data.network

import ru.byum.muscat.data.ArtistReleases

interface DiscogsAPI {
    suspend fun searchReleases(query:String) : NetworkReleaseSearchResults?

    suspend fun searchArtists(query:String) : NetworkArtistsSearchResults?

    suspend fun getArtistReleases(id: Int): ArtistReleases?

    suspend fun getRelease(releaseID: Int): ReleaseNetwork?

    suspend fun getArtist(artistID: Int) : ArtistNetwork?
}
