package ru.byum.practce1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        MyMusicDataBase.DATABASE_NAME,
        null,
        MyMusicDataBase.DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyMusicDataBase.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyMusicDataBase.SQL_DELETE_TABLE)
        onCreate(db)
    }
}