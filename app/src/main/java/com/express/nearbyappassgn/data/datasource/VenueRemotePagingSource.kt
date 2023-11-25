package com.express.nearbyappassgn.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.express.nearbyappassgn.App
import com.express.nearbyappassgn.di.modules.DataSourceEntryPoint
import com.express.nearbyappassgn.model.LatLng
import com.express.nearbyappassgn.model.Venue
import dagger.hilt.android.EntryPointAccessors
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val PAGE_SIZE = 10

class VenueRemotePagingSource @Inject constructor(
    private val range: Int, private val location: LatLng,
) :
    PagingSource<Int, Venue>() {

    private val appContext = App.getInstance()?.applicationContext ?: throw IllegalStateException()
    private val entryPoint =
        EntryPointAccessors.fromApplication(appContext, DataSourceEntryPoint::class.java)
    private val nearbyApiService = entryPoint.nearbyApiService

    override fun getRefreshKey(state: PagingState<Int, Venue>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Venue> {
        val page = params.key ?: STARTING_KEY

        return try {
            val venueResponse = nearbyApiService.fetchVenues(PAGE_SIZE, params.key ?: 1, location.latitude, location.longitude, "${range}mi")
            if (venueResponse.isSuccessful && venueResponse.body() != null) {
                LoadResult.Page(
                    data = venueResponse.body()!!.venues,
                    prevKey = if (page == STARTING_KEY) null else page,
                    nextKey = if (venueResponse.body()!!.venues.isNullOrEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("No Data"))
            }

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_KEY = 1
    }
}