package ru.byum.muscat.data.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.byum.muscat.data.ReleaseSearchResults

interface DiscogsAPI {
    suspend fun getRelease(id: Int): ReleaseNetwork?
    suspend fun onSearch(query:String) : ReleaseSearchResults?

}
