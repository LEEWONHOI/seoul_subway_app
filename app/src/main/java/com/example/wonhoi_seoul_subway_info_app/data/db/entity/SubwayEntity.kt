package com.example.wonhoi_seoul_subway_info_app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubwayEntity(    // 노선
    @PrimaryKey val subwayId : Int
)