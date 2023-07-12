package ru.byum.practce1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.EditText
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.byum.practce1.retrofit.ProductApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.byum.practce1.retrofit.AuthRequest
import ru.byum.practce1.retrofit.MainApi
import retrofit2.Call
import ru.byum.practce1.retrofit.User
import android.util.Log
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONTokener

class SearchActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)

        val bt_getRequest: Button? = findViewById(R.id.btRequest)
        val tvMyText: TextView = findViewById(R.id.myTextView)
        val tvMyText2: TextView = findViewById(R.id.myTextView2)
        val etText : EditText = findViewById(R.id.etText)

        val etText2 : EditText = findViewById(R.id.etText2)

        // журнал для отслеживания запросов на и с сервера в logcat
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


        var num: Int = 0

        fun onEditTextWatcher(): TextWatcher {
            return object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val num : Int = etText.text.toString().toInt()
                }
                override fun afterTextChanged(s: Editable) {

                }
            }
        }
        onEditTextWatcher()


        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val MainApi = retrofit.create(MainApi::class.java)

        bt_getRequest?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = MainApi.auth(AuthRequest(etText.text.toString(), etText2.text.toString()))
                Log.i(TAG, "!!!!${user}")
                user.enqueue(object: Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.code() == 200) {
                            val test = response.body()

                            Log.d(TAG, "test ${test}")
                            tvMyText.text = test?.firstName
                            tvMyText2.text = test?.lastName
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                    }
                })
                //runOnUiThread{

                //}
            }
        }
    }

}

private fun EditText.addTextChangedListener(textWatcher: TextWatcher?, function: () -> Unit) {

}

