package ru.byum.muscat.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitDiscogsAPI {
    @GET("releases/{id}")
    suspend fun getRelease(@Path("id") id: String): Response<ReleaseNetwork>
}