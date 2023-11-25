package com.express.nearbyappassgn.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.express.nearbyappassgn.data.datasource.PAGE_SIZE
import com.express.nearbyappassgn.data.datasource.VenueRemotePagingSource
import com.express.nearbyappassgn.model.LatLng
import com.express.nearbyappassgn.model.Venue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HomeVenueRepository {
    fun getVenuesByRange(location: LatLng, range: Int): Flow<PagingData<Venue>>
}

class HomeVenueRepositoryImpl @Inject constructor() :  HomeVenueRepository{
    override fun getVenuesByRange(location: LatLng, range: Int): Flow<PagingData<Venue>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                VenueRemotePagingSource(range, location)
            }
        ).flow
    }
}