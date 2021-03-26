package com.android.dcdsubfunddua.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.android.dcdsubfunddua.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_NAMEF


class FavoriteHelper(context: Context) {


    private var databaseHelper: FavoriteDatabaseHelper = FavoriteDatabaseHelper(context)
    private var database: SQLiteDatabase = databaseHelper.writableDatabase

    companion object {
        private const val DATABASE_TABLE = "favorite"
        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper =
            INSTANCE ?: synchronized(this) {
                FavoriteHelper(context).apply { INSTANCE = this }
            }


    }


    @Synchronized
    @Throws(SQLiteException::class)
    fun open() {
        database = databaseHelper.writableDatabase


    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME_NAMEF ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$COLUMN_NAME_NAMEF = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$COLUMN_NAME_NAMEF = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$COLUMN_NAME_NAMEF= '$id'", null)
    }


}