package com.example.wonhoi_seoul_subway_info_app.data.db

import androidx.room.*
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.StationEntity
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.StationSubwayCrossRefEntity
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.StationWithSubwayEntity
import com.example.wonhoi_seoul_subway_info_app.data.db.entity.SubwayEntity
import com.example.wonhoi_seoul_subway_info_app.domain.Station
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Transaction
    @Query("SELECT * FROM StationEntity")
    fun getStationWithSubways(): Flow<List<StationWithSubwayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(station: List<StationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubways(subway: List<SubwayEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossReferences(reference: List<StationSubwayCrossRefEntity>)

    @Transaction
    suspend fun insertStationSubways(stationSubways: List<Pair<StationEntity, SubwayEntity>>) {
        insertStations(stationSubways.map { it.first })
        insertSubways(stationSubways.map { it.second })
        insertCrossReferences(
            stationSubways.map { (station, subway) ->
                StationSubwayCrossRefEntity(
                    station.stationName,
                    subway.subwayId
                )
            }
        )
    }

    @Update
    suspend fun updateStation(station: StationEntity)

}