package com.android.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite (
        var id : Int? = 0,
        var nameF : String? = "",
        var userNameF : String? = "",
        var avatarF : String? = "",
        var companyF : String? = "",
        var locationF : String? = ""
) : Parcelable
