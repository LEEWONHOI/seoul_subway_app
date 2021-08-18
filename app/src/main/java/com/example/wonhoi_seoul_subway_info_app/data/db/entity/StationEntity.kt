package com.example.wonhoi_seoul_subway_info_app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationEntity(
    @PrimaryKey val stationName : String,
    val isFavorited :Boolean = false
)