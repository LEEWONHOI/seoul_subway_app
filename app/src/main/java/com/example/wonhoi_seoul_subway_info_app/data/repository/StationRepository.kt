package com.example.wonhoi_seoul_subway_info_app.data.repository

import com.example.wonhoi_seoul_subway_info_app.domain.ArrivalInformation
import com.example.wonhoi_seoul_subway_info_app.domain.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    val stations : Flow<List<Station>>

    suspend fun refreshStations()

    suspend fun getStationArrivals(stationName : String) : List<ArrivalInformation>

    suspend fun updateStation(station: Station)

}