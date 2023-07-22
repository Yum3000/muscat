package ru.byum.muscat.data.network

import android.util.Log
import ru.byum.muscat.data.ArtistsSearchResults
import ru.byum.muscat.data.ReleaseSearchResult
import ru.byum.muscat.data.ReleaseSearchResults
import javax.inject.Inject


public const val TAG = "DefaultDiscogsAPI"


class DefaultDiscogsAPI @Inject constructor(): DiscogsAPI {
    private val retrofit = RetrofitDiscogsClient.getClient()
    private val api = retrofit.create(RetrofitDiscogsAPI::class.java)

    override suspend fun getRelease(id: Int): ReleaseNetwork? {
        val response = api.getRelease(id)
        if(response.isSuccessful) {
            Log.d(TAG, "!!!test {$response.body}")
            return response.body()
        }

        return ReleaseNetwork(id, "bad response", "0000")
    }

    override suspend fun searchReleases(query: String): ReleaseSearchResults? {
        val response = api.onSearch(query, "release", "MligNYkNjltvekeFWwkfXvNVejHZjkewRsYBgVoQ")
        if(response.isSuccessful) {
            Log.d(TAG, "!!!test2 {$response.body}")
            return response.body()
        }
        //response.errorBody()
        Log.d(TAG, "!!!WRONG {$response.body}")
        return response.body()
    }

    override suspend fun searchArtists(query: String): ArtistsSearchResults? {
        val response = api.onSearchArtists(query, "artist", "MligNYkNjltvekeFWwkfXvNVejHZjkewRsYBgVoQ")
        if(response.isSuccessful) {
            Log.d(TAG, "!!!test2 {$response.body}")
            return response.body()
        }
        //response.errorBody()
        Log.d(TAG, "!!!WRONG {$response.body}")
        return response.body()
    }
}



