package com.express.nearbyappassgn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.express.nearbyappassgn.data.HomeVenueRepository
import com.express.nearbyappassgn.model.LatLng
import com.express.nearbyappassgn.model.VenueUIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeVenueViewModel @Inject constructor(private val homeVenueRepository: HomeVenueRepository) : ViewModel() {

    val _range = MutableLiveData(0)
    val range: LiveData<Int> = _range

    val currentLatLngLiveData = MutableLiveData<LatLng>()

    fun fetchVenueList(newRange: Int, currentLatLng: LatLng): Flow<PagingData<VenueUIItem>> {
        return if (currentLatLngLiveData.value != null) {
            currentLatLngLiveData.value = currentLatLng
            homeVenueRepository.getVenuesByRange(currentLatLng, newRange)
                .map { pagingData ->
                    pagingData.map { VenueUIItem.mapVenueToUiObject(it) }
                }.cachedIn(viewModelScope)
        } else {
            // If currentLatLng is null, emit an empty PagingData
            flowOf(PagingData.empty())
        }
    }

    fun fetchVenueListFromRange(): Flow<PagingData<VenueUIItem>> {
        return range.asFlow()
            .filter { currentLatLngLiveData.value != null }
            .debounce(1000)
            .distinctUntilChanged()
            .flatMapLatest {
                homeVenueRepository.getVenuesByRange(currentLatLngLiveData.value!!, it)
                    .map { pagingData ->
                        pagingData.map { mappedData -> VenueUIItem.mapVenueToUiObject(mappedData) }
                    }.cachedIn(viewModelScope)
            }
    }

    fun updateRange(newRange: Int) {
        _range.value = newRange.toInt()
    }

    fun setCurrentLocation(latLng: LatLng) {
        currentLatLngLiveData.value = latLng
    }

}