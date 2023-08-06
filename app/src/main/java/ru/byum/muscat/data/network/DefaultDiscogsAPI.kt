package ru.byum.muscat.data.network

import retrofit2.Response
import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResults
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

    override suspend fun searchReleases(query: String): ReleaseSearchResults? {
        val response = api.onSearch(query, "release", token)

        return response.body()
    }

    override suspend fun searchArtists(query: String): ArtistsSearchResults? {
        val response = api.onSearchArtists(query, "artist", token)

        return response.body()
    }

    override suspend fun getRelease(releaseID: Int): ReleaseNetwork? {
       val response = api.getRelease(releaseID, token)
        return response.body()
    }
}



