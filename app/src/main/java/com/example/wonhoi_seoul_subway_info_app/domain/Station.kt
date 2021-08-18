package com.example.wonhoi_seoul_subway_info_app.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(
    val name : String,
    val isFavorited : Boolean,
    val connectedSubway : List<Subway>
) : Parcelable