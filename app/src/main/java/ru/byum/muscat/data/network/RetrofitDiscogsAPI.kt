package ru.byum.muscat.data.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.byum.muscat.data.ReleaseSearchResults

interface RetrofitDiscogsAPI {

    @GET("/releases/{id}")
    suspend fun getRelease(@Path("id") id: Int): Response<ReleaseNetwork>

    @GET("/database/search?")
    suspend fun onSearch(@Query("query") query: String, @Query("type") type : String,
                         @Query("token") token:String) :
            Response<ReleaseSearchResults>
}