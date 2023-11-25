package com.express.nearbyappassgn.data

import com.express.nearbyappassgn.BuildConfig
import com.express.nearbyappassgn.model.VenueResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbyApiInterface {
    companion object {
        const val BASE_URL = "${BuildConfig.NEARBY_API_BASE}/"
    }

    @GET("/venues")
    suspend fun fetchVenues(
        @Query("per_page") pageSize: Int,
        @Query("page") page: Int,
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("range") range: String, //12mi
    ): Response<VenueResponse>
}