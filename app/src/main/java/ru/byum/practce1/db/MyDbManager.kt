package ru.byum.practce1.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db:SQLiteDatabase? = null

    fun openDb(){
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title:String, content:String){
        val values = ContentValues().apply{
            put(MyMusicDataBase.COLUMN_NAME_TITLE, title)
            put(MyMusicDataBase.COLUMN_NAME_CONTENT, content)
        }
        db?.insert(MyMusicDataBase.TABLE_NAME, null, values)
    }

    fun readDbData() :ArrayList<String> {
        val dataList = ArrayList<String>()

        val cursor = db?.query(MyMusicDataBase.TABLE_NAME, null, null, null,
            null, null, null)


        while(cursor?.moveToNext()!!) { // двигаемся, если не null
            val dataText = cursor.getString(cursor.getColumnIndexOrThrow(MyMusicDataBase.COLUMN_NAME_TITLE))
            dataList.add(dataText.toString())
        }
        cursor.close()
        return dataList
    }

    fun closeDb(){
        myDbHelper.close()
    }
}