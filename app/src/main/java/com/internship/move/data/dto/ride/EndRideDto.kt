package com.internship.move.data.dto.ride

import com.internship.move.data.model.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EndRideDto(
    @Json(name = "_id") val rideId: String,
    @Json(name = "userId") val userId: String,
    @Json(name = "scooterId") val scooterId: String,
    @Json(name = "duration") val duration: Int,
    @Json(name = "distance") val distance: Int,
    @Json(name = "allLocation") val allLocation: List<Location>,
    @Json(name = "status") val status: String
)