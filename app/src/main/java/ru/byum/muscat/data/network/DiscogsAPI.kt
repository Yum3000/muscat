package ru.byum.muscat.data.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DiscogsAPI {

    suspend fun getRelease(@Path("id") id: String): ReleaseNetwork?

}
