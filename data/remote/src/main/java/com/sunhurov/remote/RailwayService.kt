package com.sunhurov.remote

import com.sunhurov.model.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RailwayService {

    @GET("/api/v2/main/stations")
    fun fetchStationsAsync(): Deferred<List<Station>>

    @GET("/api/v2/main/station_keywords")
    fun fetchStationKeywordsAsync(): Deferred<List<StationKeyword>>

}