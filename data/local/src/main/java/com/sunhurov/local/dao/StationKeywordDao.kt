package com.sunhurov.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunhurov.model.StationKeyword


@Dao
abstract class StationKeywordDao {


    @Query("SELECT stationId FROM StationKeyword WHERE keyword LIKE '%' || :stationName || '%'")
    abstract suspend fun loadByStationName(stationName: String): List<Int>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(keywords: List<StationKeyword>)

    @Query("SELECT * FROM StationKeyword LIMIT 1")
    abstract suspend fun getAnyKeyword(): StationKeyword


}