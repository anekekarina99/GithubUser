package com.android.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite (
        var name : String? = null,
        var username : String? = null,
        var avatar : String? = null,
        var company : String? = null,
        var location: String? = null
) : Parcelable
