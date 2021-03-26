package com.android.dcdsubfunddua.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.dcdsubfunddua.database.FavoriteDatabaseContract.FavoriteColumns.Companion.TABLE_NAME


class FavoriteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
                " (${FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_NAMEF} TEXT PRIMARY KEY NOT NULL ," +
                "${FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                " ${FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_AVATAR_URL} TEXT NOT NULL," +
                " ${FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_COMPANY} TEXT NOT NULL,"+
                " ${FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_LOCATION} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}