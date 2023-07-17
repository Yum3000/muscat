package ru.byum.muscat.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.discogs.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val create: DiscogsAPI by lazy {
        retrofit.create(DiscogsAPI::class.java)
    }
}