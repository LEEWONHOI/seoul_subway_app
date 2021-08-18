package com.example.wonhoi_seoul_subway_info_app.data.api

import com.example.wonhoi_seoul_subway_info_app.data.db.entity.StationEntity
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.SubwayEntity

interface StationApi {

    suspend fun getStationDataUpdatedTimeMillis() : Long

    suspend fun getStationSubways() : List<Pair<StationEntity, SubwayEntity>>

}