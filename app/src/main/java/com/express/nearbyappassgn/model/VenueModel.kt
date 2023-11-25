package com.express.nearbyappassgn.model

data class Venue(
    val name: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val url: String,
    val postalCode: String,
    val address: String?,
    val state: String?,
    val timeZone: String?,
    val upcomingEvents: Boolean,
    val numUpcomingEvents: Int,
    val id: Int,
    val popularity: Int,
    val accessMethod: String?,
    val metroCode: Int,
    val capacity: Int,
    val displayLocation: String
)

data class VenueUIItem(
    val id: Int,
    val name: String,
    val address: String?
) {
    companion object {
        fun mapVenueToUiObject(venue: Venue): VenueUIItem {
            return VenueUIItem(
                venue.id,
                venue.name,
                venue.address
            )
        }
    }
}

data class VenueResponse(val venues: List<Venue>)

data class LatLng(
    val latitude: Double,
    val longitude: Double
)
