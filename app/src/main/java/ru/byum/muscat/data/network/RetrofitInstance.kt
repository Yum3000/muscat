package ru.byum.muscat.data.network

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.discogs.com")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    val create: DiscogsAPI by lazy {
        retrofit.create(DiscogsAPI::class.java)
    }
}