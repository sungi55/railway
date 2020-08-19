package com.sunhurov.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunhurov.common.base.BaseViewModel
import com.sunhurov.common.utils.Event
import com.sunhurov.detail.R
import com.sunhurov.details.domain.GetDistanceDetailsUseCase
import com.sunhurov.model.Station
import com.sunhurov.repository.AppDispatchers
import com.sunhurov.repository.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [DetailFragment].
 */
class DetailViewModel(
    private val getDistanceDetailUseCase: GetDistanceDetailsUseCase,
    private val dispatchers: AppDispatchers
): BaseViewModel() {

    // FOR DATA
    private val _distanceDetails = MediatorLiveData<Resource<Pair<Station, Station>>>()

    val distanceDetails: LiveData<Resource<Pair<Station, Station>>> get() = _distanceDetails

    // PRIVATE DATA
    private var distanceDetailsSource: LiveData<Resource<Pair<Station, Station>>> = MutableLiveData()


    // PUBLIC ACTIONS ---
    fun loadDetailsWhenActivityStarts(startId: Int, endId:Int ) {
        getDistanceDetail(startId, endId)
    }


    // ---

    private fun getDistanceDetail(startId: Int, endId:Int): Job {
        return viewModelScope.launch(dispatchers.main) {
            // We make sure there is only one source of livedata (allowing us properly refresh)
            _distanceDetails.removeSource(distanceDetailsSource)

            withContext(dispatchers.io) {
                distanceDetailsSource =
                    getDistanceDetailUseCase(startStationId = startId, endStationId =  endId)
            }

            _distanceDetails.addSource(distanceDetailsSource) {
                    _distanceDetails.value = it
                if (it.status == Resource.Status.ERROR)
                    _snackbarError.value = Event(R.string.an_error_happened)
            }
        }
    }

}