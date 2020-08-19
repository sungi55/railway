package com.sunhurov.repository.station

import androidx.lifecycle.LiveData
import com.sunhurov.model.Station
import com.sunhurov.model.StationKeyword
import com.sunhurov.repository.utils.Resource

interface StationRepository {
    suspend fun saveStations(stations: List<Station>)
    suspend fun saveStationsKeywords(keywords: List<StationKeyword>)
    suspend fun getStationsByName(stationName: String): LiveData<Resource<List<Station>>>
    suspend fun getStations(): LiveData<Resource<Boolean>>
    suspend fun getStationDetails(startStationId: Int, endStationId: Int): LiveData<Resource<Pair<Station, Station>>>
}