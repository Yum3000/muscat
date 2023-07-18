package ru.byum.muscat.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RetrofitDiscogsAPI {

    @Headers("User-Agent: muscat/0.1 + https://github.com/Yum3000")
    @GET("/releases/{id}")
    fun getRelease(@Path("id") id: String): Call<ReleaseNetwork?>
}