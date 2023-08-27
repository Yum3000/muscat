package ru.byum.muscat.data.network

import ru.byum.muscat.data.ArtistReleases
import javax.inject.Inject


public const val TAG = "DefaultDiscogsAPI"

const val token = "MligNYkNjltvekeFWwkfXvNVejHZjkewRsYBgVoQ"

class DefaultDiscogsAPI @Inject constructor(): DiscogsAPI {
    private val retrofit = RetrofitDiscogsClient.getClient()
    private val api = retrofit.create(RetrofitDiscogsAPI::class.java)

    override suspend fun getArtistReleases(id: Int?): ArtistReleases? {
        val response = api.getArtistReleases(id)

        return response.body()
    }

    override suspend fun searchReleases(query: String): NetworkReleaseSearchResults? {
        val response = api.onSearch(query, "master", token)

        return response.body()
    }

    override suspend fun searchArtists(query: String): NetworkArtistsSearchResults? {
        val response = api.onSearchArtists(query, "artist", token)

        return response.body()
    }

    override suspend fun getRelease(releaseID: Int): ReleaseNetwork? {
       val response = api.getRelease(releaseID, token)
        return response.body()
    }

    override suspend fun getArtist(artistID: Int): ArtistNetwork? {
        val response = api.getArtist(artistID, token)
        return response.body()
    }
}



