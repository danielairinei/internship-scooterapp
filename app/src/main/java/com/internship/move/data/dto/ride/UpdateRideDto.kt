package com.internship.move.data.dto.ride

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateRideDto(
    @Json(name = "userId") val userId: String,
    @Json(name = "scooterId") val scooterId: String,
    @Json(name = "_id") val rideId: String
)