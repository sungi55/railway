package com.sunhurov.home.domain

import androidx.lifecycle.LiveData
import com.sunhurov.model.Station
import com.sunhurov.repository.station.StationRepository
import com.sunhurov.repository.utils.Resource

/**
 * Use case that gets a [Resource][List][Station] from [StationRepository]
 */
class GetStationsUseCase(private val repository: StationRepository) {

    suspend operator fun invoke(): LiveData<Resource<Boolean>> {
        return repository.getStations()
    }

}