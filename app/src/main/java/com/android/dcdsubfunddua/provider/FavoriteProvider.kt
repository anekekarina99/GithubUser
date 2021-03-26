package com.android.dcdsubfunddua.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.android.dcdsubfunddua.database.FavoriteDatabaseContract.AUTHORITY
import com.android.dcdsubfunddua.database.FavoriteDatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.android.dcdsubfunddua.database.FavoriteDatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.android.dcdsubfunddua.database.FavoriteHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAV = 1
        private const val FAV_ID = 2
        private lateinit var favoriteHelper: FavoriteHelper
        private val favUriMatcher = UriMatcher(UriMatcher.NO_MATCH)


        init {
            favUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
            favUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                FAV_ID
            )
        }
    }


    //  "Implement this to handle requests to delete one or more rows"
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
      val deleted : Int = when(FAV_ID){
          favUriMatcher.match(uri) -> favoriteHelper.deleteById(uri.lastPathSegment.toString())
          else -> 0
      }
        context?.contentResolver?.notifyChange(CONTENT_URI,null )
        return deleted
    }

    //"Implement this to handle requests for the MIME type of the data" + "at the given URI"
    override fun getType(uri: Uri): String? {
        return null
    }

    // "Implement this to handle requests to insert a new row."
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            favUriMatcher.match(uri) -> favoriteHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    //"Implement this to initialize your content provider on startup."
    override fun onCreate(): Boolean {
        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }

    //"Implement this to handle query requests from clients."
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {
        return when (favUriMatcher.match(uri)) {
            FAV -> favoriteHelper.queryAll()
            FAV_ID -> favoriteHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    //"Implement this to handle requests to update one or more rows."
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        val updated : Int = when(FAV_ID){
            favUriMatcher.match(uri) -> favoriteHelper.update(
                uri.lastPathSegment.toString(),
                values
            )
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return updated
    }
}