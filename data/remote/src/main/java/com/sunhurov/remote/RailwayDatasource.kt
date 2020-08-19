package com.sunhurov.remote

class RailwayDatasource(private val railwayService: RailwayService) {

    fun fetchStationsAsync()
            = railwayService.fetchStationsAsync()

    fun fetchStationKeywordsAsync()
            = railwayService.fetchStationKeywordsAsync()

}