package ru.byum.muscat.data.network

import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResults

interface DiscogsAPI {
    suspend fun getRelease(id: Int): ReleaseNetwork?
    suspend fun searchReleases(query:String) : ReleaseSearchResults?

    suspend fun searchArtists(query:String) : ArtistsSearchResults?

}
