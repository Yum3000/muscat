package ru.byum.practce1.db

import android.provider.BaseColumns

object MyMusicDataBase {
    const val TABLE_NAME = "Folder"
    const val COLUMN_NAME_TITLE = "Name"
    const val COLUMN_NAME_CONTENT = "Content"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "MyMusic.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXIST $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY,$COLUMN_NAME_TITLE TEXT,$COLUMN_NAME_CONTENT TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"


}