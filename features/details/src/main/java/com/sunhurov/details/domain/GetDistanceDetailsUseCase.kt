package com.sunhurov.details.domain

import androidx.lifecycle.LiveData
import com.sunhurov.model.Station
import com.sunhurov.repository.station.StationRepository
import com.sunhurov.repository.utils.Resource

/**
 * Use case that gets a [Resource] [Pair] [Station] from [StationRepository]
 * and makes some specific logic actions on it.
 *
 */
class GetDistanceDetailsUseCase(private val repository: StationRepository) {

    suspend operator fun invoke(startStationId: Int,
                                endStationId:Int
    ): LiveData<Resource<Pair<Station, Station>>> {
        return repository.getStationDetails(startStationId, endStationId)
    }
}