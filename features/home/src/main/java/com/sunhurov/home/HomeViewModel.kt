package com.sunhurov.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunhurov.common.base.BaseViewModel
import com.sunhurov.common.utils.Event
import com.sunhurov.home.domain.GetStationsUseCase
import com.sunhurov.home.domain.SearchStationsUseCase
import com.sunhurov.model.Station
import com.sunhurov.repository.AppDispatchers
import com.sunhurov.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [HomeFragment].
 */
class HomeViewModel(
    private val searchStationsUseCase: SearchStationsUseCase,
    private val loadStationsUseCase: GetStationsUseCase,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    // FOR DATA
    private val _loading = MediatorLiveData<Resource<Boolean>>()
    private val _startStation = MediatorLiveData<Resource<List<Station>>>()
    private val _endStation = MediatorLiveData<Resource<List<Station>>>()


    private var endStationResult: Station? = null
    private var startStationResult: Station? = null

    val loading: LiveData<Resource<Boolean>> get() = _loading
    val startStation: LiveData<Resource<List<Station>>> get() = _startStation
    val endStation: LiveData<Resource<List<Station>>> get() = _endStation

    private var startStationSource: LiveData<Resource<List<Station>>> = MutableLiveData()
    private var endStationSource: LiveData<Resource<List<Station>>> = MutableLiveData()
    private var loadingSource: LiveData<Resource<Boolean>> = MutableLiveData()


    // PUBLIC ACTIONS ---
    fun onShowDetailsClick() {
        if(endStationResult != null && startStationResult !=null)
            navigate(HomeFragmentDirections
                .actionHomeFragmentToDetailFragment(startStationResult!!.id, endStationResult!!.id))
    }

    fun onStationEndSelected(stationTo: Int) {
        endStationResult = endStation.value?.data?.get(stationTo)
    }

    fun onStationStartSelected(stationTo: Int) {
        startStationResult = startStation.value?.data?.get(stationTo)
    }

    fun onStartStationTextChanged(name:String) {
        searchStartStation(name)
    }

    fun onEndStationTextChanged(name: String) {
        searchEndStation(name)
    }

    fun loadStations() = getStations()


    // ---

    private fun getStations() = viewModelScope.launch(dispatchers.main) {
        // We make sure there is only one source of livedata (allowing us properly refresh)
        _loading.removeSource(loadingSource)

        withContext(dispatchers.io) {
            loadingSource = loadStationsUseCase()
        }
        _loading.addSource(loadingSource) {
            _loading.value = it
            if (it.status == Resource.Status.ERROR)
                _snackbarError.value = Event(R.string.text_an_error_happend)
        }
    }


    private fun searchStartStation(name: String)  = viewModelScope.launch(dispatchers.main) {
        // We make sure there is only one source of livedata (allowing us properly refresh)
        _startStation.removeSource(startStationSource)

        withContext(dispatchers.io) {
            startStationSource = searchStationsUseCase(stationName = name)
        }
        _startStation.addSource(startStationSource) {
            if(it.status == Resource.Status.SUCCESS)
                _startStation.value = it
        }
    }

    private fun searchEndStation(name: String)  = viewModelScope.launch(dispatchers.main) {
        // We make sure there is only one source of livedata (allowing us properly refresh)
        _endStation.removeSource(endStationSource)

        withContext(dispatchers.io) {
            endStationSource = searchStationsUseCase(stationName = name)
        }
        _endStation.addSource(endStationSource) {
            if(it.status == Resource.Status.SUCCESS)
                _endStation.value = it
        }
    }

}





