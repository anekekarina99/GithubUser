package com.android.dcdsubfunddua.helper

import android.database.Cursor
import com.android.dcdsubfunddua.database.FavoriteDatabaseContract
import com.android.dcdsubfunddua.model.Favorite
import java.util.ArrayList

object MapHelper {

    fun mapCursorToArrayList(favorCursor: Cursor?): ArrayList<Favorite> {
        val myList = ArrayList<Favorite>()

        favorCursor?.apply {
            while (moveToNext()) {
                val usernameF =
                    getString(getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_USERNAME))
                val nameF =
                    getString(getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_NAMEF))
                val avatarF =
                    getString(getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_AVATAR_URL))
                val companyF =
                    getString(getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_COMPANY))
                val locationF =
                    getString(getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteColumns.COLUMN_NAME_LOCATION))

                myList.add(
                    Favorite(
                        usernameF,
                        nameF,
                        avatarF,
                        companyF,
                        locationF
                    )
                )
            }
        }
        return myList
    }
}