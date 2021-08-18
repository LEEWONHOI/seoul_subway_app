package com.example.wonhoi_seoul_subway_info_app.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class StationWithSubwayEntity(
        // @Embedded 를 사용해서 stationName, isFavorited 값을 묶어서 가지고 옴
    @Embedded val station : StationEntity,

    @Relation(  parentColumn = "stationName",
                entityColumn = "subwayId",
                associateBy = Junction(StationSubwayCrossRefEntity::class))
    val subway : List<SubwayEntity>
    // station (지하철에 대한) : subway (지하철 호선에 대한 다수의 정보를 맵핑함)
)