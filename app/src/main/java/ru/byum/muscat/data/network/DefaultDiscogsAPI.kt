package ru.byum.muscat.data.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Tag
import ru.byum.muscat.data.Release
import javax.inject.Inject
import okhttp3.logging.HttpLoggingInterceptor


public const val TAG = "DefaultDiscogsAPI"


class DefaultDiscogsAPI @Inject constructor(
    ): DiscogsAPI {

    override suspend fun getRelease(id: String): ReleaseNetwork? {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        //val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder().baseUrl("https://api.discogs.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val MainApi = retrofit.create(RetrofitDiscogsAPI::class.java)

        val call: Call<ReleaseNetwork?>? = MainApi.getRelease("1234")
//
//        val response = call!!.enqueue(object : Callback<ReleaseNetwork?> {
//            override fun onResponse(
//                call: Call<ReleaseNetwork?>,
//                response: Response<ReleaseNetwork?>
//            ) {
//                if (response.isSuccessful) {
//                    Log.d(TAG, "!!!test ${response.body().toString()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ReleaseNetwork?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })

        val response = call!!.execute()
        val release = response.body()
        Log.d(TAG, "!!!test ${response.body().toString()}")
        return release

    }
}
       // return ReleaseNetwork(id, "title", "1999")


