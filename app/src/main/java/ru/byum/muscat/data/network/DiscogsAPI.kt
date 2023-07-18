package ru.byum.muscat.data.network

interface DiscogsAPI {
    suspend fun getRelease(id: Int): ReleaseNetwork?
}