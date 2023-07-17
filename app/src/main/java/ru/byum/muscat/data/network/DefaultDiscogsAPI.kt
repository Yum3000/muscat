package ru.byum.muscat.data.network

import retrofit2.Call
import javax.inject.Inject

class DefaultDiscogsAPI @Inject constructor(): DiscogsAPI {
    override suspend fun getRelease(id: String): ReleaseNetwork? {
        return ReleaseNetwork(id, "title", "1999")
    }
}

