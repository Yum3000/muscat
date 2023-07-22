package ru.byum.muscat.data.network

import android.util.Log
import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResults
import javax.inject.Inject


public const val TAG = "DefaultDiscogsAPI"


class DefaultDiscogsAPI @Inject constructor(): DiscogsAPI {
    private val retrofit = RetrofitDiscogsClient.getClient()
    private val api = retrofit.create(RetrofitDiscogsAPI::class.java)

    override suspend fun getArtistReleases(id: Int?): ArtistReleases? {
        val response = api.getArtistReleases(id)

        return response.body()
    }

    override suspend fun searchReleases(query: String): ReleaseSearchResults? {
        val response = api.onSearch(query, "release", "MligNYkNjltvekeFWwkfXvNVejHZjkewRsYBgVoQ")

        return response.body()
    }

    override suspend fun searchArtists(query: String): ArtistsSearchResults? {
        val response = api.onSearchArtists(query, "artist", "MligNYkNjltvekeFWwkfXvNVejHZjkewRsYBgVoQ")

        return response.body()
    }
}



