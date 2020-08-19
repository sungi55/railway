package com.sunhurov.repository.station

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunhurov.local.dao.StationDao
import com.sunhurov.local.dao.StationKeywordDao
import com.sunhurov.model.Station
import com.sunhurov.model.StationKeyword
import com.sunhurov.pref.PreferenceHelper
import com.sunhurov.remote.RailwayDatasource
import com.sunhurov.repository.utils.Resource
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class StationRepositoryImpl(
    private val stationDao: StationDao,
    private val keywordDao: StationKeywordDao,
    private val datasource:RailwayDatasource,
    private val pref:PreferenceHelper
): StationRepository {


    override suspend fun getStationDetails(
        startStationId: Int,
        endStationId: Int
    ):  LiveData<Resource<Pair<Station, Station>>> {
        val result = MutableLiveData<Resource<Pair<Station,Station>>>()
        val supervisorJob = SupervisorJob()

        withContext(Dispatchers.Main) {
            result.value = Resource.loading(null)
        }

        CoroutineScope(coroutineContext).launch(supervisorJob) {

            try {
                val startStation:Station = stationDao.loadStationById(startStationId)
                val endStation:Station = stationDao.loadStationById(endStationId)

                result.postValue(Resource.success(Pair(startStation, endStation)))
            } catch (e: Exception) {
                Log.e("StationRepository", "An error happened: $e")
                result.postValue(Resource.error(e, null))
            }
        }

        return result
    }

    override suspend fun getStations(): LiveData<Resource<Boolean>> {
        val result = MutableLiveData<Resource<Boolean>>()
        val supervisorJob = SupervisorJob()

        withContext(Dispatchers.Main) {
            result.value = Resource.loading(null)
        }

        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val station:Station? = stationDao.getAnyStation()
            val keyword:StationKeyword? = keywordDao.getAnyKeyword()
            val shouldRefresh = pref.getLastSyncDate()

            if(shouldFetch(station, keyword, shouldRefresh)) {
                try {
                    val keywords = datasource.fetchStationKeywordsAsync().await()
                    val stations = datasource.fetchStationsAsync().await()

                    keywordDao.insert(keywords)
                    stationDao.insert(stations)
                    pref.setLastSyncDate(Date().time)

                    result.postValue(Resource.success(true))
                } catch (e: Exception) {
                    Log.e("StationRepository", "An error happened: $e")
                    result.postValue(Resource.error(e,false))
                }
            } else result.postValue(Resource.success(true))

        }

        return result
    }

    private fun shouldFetch(
        station: Station?,
        keyword: StationKeyword?,
        shouldRefresh: Long
    ) = station == null
            || keyword == null
            || haveToRefreshFromNetwork(shouldRefresh)


    override suspend fun saveStations(stations: List<Station>) {
        stationDao.insert(stations)
    }

    override suspend fun saveStationsKeywords(keywords: List<StationKeyword>) {
        keywordDao.insert(keywords)
    }


    override suspend fun getStationsByName(stationName: String): LiveData<Resource<List<Station>>> {

        val result = MutableLiveData<Resource<List<Station>>>()
        val supervisorJob = SupervisorJob()

        withContext(Dispatchers.Main) {
            result.value = Resource.loading(null)
        }

        CoroutineScope(coroutineContext).launch(supervisorJob) {

            try {
                val keywordResult = keywordDao.loadByStationName(stationName)
                val stations = stationDao.findByIds(keywordResult)
                result.postValue(Resource.success(stations))
            } catch (e: Exception) {
                Log.e("StationRepository", "An error happened: $e")
                result.postValue(Resource.error(e, arrayListOf()))
            }

        }

        return result
    }

    /**
     * We consider that an [Station] and [StationKeyword] is outdated when the last time
     * we fetched it was more than 24h
     */
    private fun haveToRefreshFromNetwork(lastRefreshed: Long) : Boolean
            = TimeUnit.MILLISECONDS.toHours(Date().time - Date(lastRefreshed).time) >= 24


}