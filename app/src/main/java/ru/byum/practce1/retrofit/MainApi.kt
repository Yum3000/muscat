package ru.byum.practce1.retrofit

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.Call
import retrofit2.Response

interface MainApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id : Int): Product

    @POST("auth/login")
    fun auth(@Body authRequest: AuthRequest): Call<User>
}