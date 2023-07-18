package ru.byum.muscat.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


object RetrofitDiscogsClient {
    // Base URL must end in /
    private const val BASE_URL = "https://api.discogs.com/"
//    private const val BASE_URL = "http://10.0.2.2:8080/"
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(UserAgentInterceptor)
        .build()

    fun getClient(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
}

object UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeader = chain.request()
            .newBuilder()
            .header("User-Agent", "muscat/0.1 +http://github.com/Yum3000").build()
        return chain.proceed(requestWithHeader)
    }
}