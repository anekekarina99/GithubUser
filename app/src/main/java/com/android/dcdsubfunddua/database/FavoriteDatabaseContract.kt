package com.android.dcdsubfunddua.database

import android.net.Uri
import android.provider.BaseColumns

object FavoriteDatabaseContract {
    const val AUTHORITY = "com.android.dcdsubfunddua.provider"
    const val SCHEME = "content"
     class FavoriteColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "favorite"
            const val COLUMN_NAME_NAMEF = "name"
            const val COLUMN_NAME_USERNAME = "username"
            const val COLUMN_NAME_AVATAR_URL = "avatar"
            const val COLUMN_NAME_COMPANY = "company"
            const val COLUMN_NAME_LOCATION = "location"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}