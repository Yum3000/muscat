package ru.byum.practce1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.byum.practce1.db.MyDbManager
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.EditText
import android.widget.Button

class FoldersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folders)

        val myDbManager = MyDbManager(this)
        val tvTest: TextView = findViewById(R.id.tvTest)
        val edTitle: EditText = findViewById(R.id.edTitle)
        val edContent: EditText = findViewById(R.id.edContent)
        //tvTest.text = ""
        myDbManager.openDb()
//        for(item in myDbManager.readDbData()){
//            tvTest.append(item)
//            tvTest.append("/n")
//        }

        fun onClickSave(view: View?){
            tvTest.text = ""

            myDbManager.insertToDb(edTitle.text.toString(), edContent.text.toString())
            val dataList = myDbManager.readDbData()
            for(item in dataList){
                tvTest.append(item)
                tvTest.append("/n")
            }
        }
        fun onDestroy(){
            super.onDestroy()
            myDbManager.closeDb()
        }
    }


}