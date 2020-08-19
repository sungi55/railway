package com.sunhurov.local.dao

import androidx.room.*
import com.sunhurov.model.Station
import com.sunhurov.model.StationKeyword

@Dao
abstract class StationDao  {

    @Query("SELECT * FROM Station WHERE id IN(:stationIds) ORDER BY hits DESC")
    abstract suspend fun findByIds(stationIds: List<Int>): List<Station>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(stations: List<Station>)

    @Query("SELECT * FROM Station LIMIT 1")
    abstract suspend fun getAnyStation(): Station

    @Query("SELECT * FROM Station WHERE id = :stationId")
    abstract suspend fun loadStationById(stationId: Int): Station

}