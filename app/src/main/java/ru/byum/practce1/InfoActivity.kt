package ru.byum.practce1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView


class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val ibtInfoBack: ImageButton = findViewById<ImageButton>(R.id.ibtInfoBack)

        ibtInfoBack.setOnClickListener(){
            Log.i(TAG, "IT HAPPEND AGAIN")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }




}