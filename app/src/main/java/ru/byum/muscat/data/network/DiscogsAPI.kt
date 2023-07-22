package ru.byum.muscat.data.network

import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResults

interface DiscogsAPI {

    suspend fun searchReleases(query:String) : ReleaseSearchResults?

    suspend fun searchArtists(query:String) : ArtistsSearchResults?

    suspend fun getArtistReleases(id: Int?): ArtistReleases?

}
