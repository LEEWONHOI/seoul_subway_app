package com.example.wonhoi_seoul_subway_info_app.data.db.entity.mapper

import com.example.wonhoi_seoul_subway_info_app.data.db.entity.StationEntity
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.StationWithSubwayEntity
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.SubwayEntity
import com.example.wonhoi_seoul_subway_info_app.domain.Station
import com.example.wonhoi_seoul_subway_info_app.domain.Subway

fun StationWithSubwayEntity.toStation() = Station(
    name = station.stationName,
    isFavorited = station.isFavorited,
    connectedSubway = subway.toSubways()    // { Subway.findById(it.subwayId) } 를 사용해서 아이디 뿐만아니라 subway 의 다른 정보들도 함께 넣어줌.
)

fun Station.toStationEntitiy() =
    StationEntity(
        stationName = name,
        isFavorited = isFavorited
    )

fun List<StationWithSubwayEntity>.toStations() = map { it.toStation() }

fun List<SubwayEntity>.toSubways() : List<Subway> = map { Subway.findById(it.subwayId) }