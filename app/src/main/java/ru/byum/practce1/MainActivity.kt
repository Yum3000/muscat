package ru.byum.practce1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import android.widget.Button
import android.util.Log
import android.content.Intent
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.byum.practce1.retrofit.Product
import ru.byum.practce1.retrofit.ProductApi

public const val TAG = "MainActivity"

class MainActivity: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btSearch: Button = findViewById<Button>(R.id.btSearch)
        val btFolders: Button = findViewById<Button>(R.id.btFolders)
        val btInfo: Button = findViewById<Button>(R.id.btInfo)

        val tv : TextView = findViewById(R.id.myTextView)

        btSearch.setOnClickListener {
            Log.i(TAG, "GO TO SEARCH")
            val intent1 = Intent(this, SearchActivity2::class.java)
            startActivity(intent1)
        }

        btFolders.setOnClickListener {
            Log.i(TAG, "GO TO FOLDERS")
            val intent2 = Intent(this, FoldersActivity::class.java)
            startActivity(intent2)
        }

        btInfo.setOnClickListener {
            Log.i(TAG, "GO TO INFO")
            val intent3 = Intent(this, InfoActivity::class.java)
            startActivity(intent3)
        }

    }

}