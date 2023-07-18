package ru.byum.muscat.data.network

import javax.inject.Inject

class DefaultDiscogsAPI @Inject constructor(): DiscogsAPI {
    private val retrofit = RetrofitDiscogsClient.getClient()
    private val api = retrofit.create(RetrofitDiscogsAPI::class.java)

    override suspend fun getRelease(id: Int): ReleaseNetwork? {
        val response = api.getRelease(id)
        if(response.isSuccessful) {
            return response.body()
        }

        return ReleaseNetwork(id, "bad response", "0000")
    }
}

