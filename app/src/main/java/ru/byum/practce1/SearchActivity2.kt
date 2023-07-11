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

class SearchActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)

        val bt_getRequest: Button? = findViewById(R.id.btRequest)
        val tvMyText: TextView = findViewById(R.id.myTextView)
        val etText : EditText = findViewById(R.id.etText)

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


        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val productApi = retrofit.create(ProductApi::class.java)

        bt_getRequest?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.getProductById(num)
                runOnUiThread{
                    tvMyText.text = product.title
                }
            }
        }
    }

}

private fun EditText.addTextChangedListener(textWatcher: TextWatcher?, function: () -> Unit) {

}

