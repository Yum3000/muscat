package ru.byum.muscat.data.network

import javax.inject.Inject

class DefaultDiscogsAPI @Inject constructor(): DiscogsAPI {
    override suspend fun getRelease(id: String): ReleaseNetwork? {
        return ReleaseNetwork(id, "test title", "1999")
    }
}

