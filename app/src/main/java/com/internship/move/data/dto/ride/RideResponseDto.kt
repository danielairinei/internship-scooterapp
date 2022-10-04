package com.internship.move.data.dto.ride

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateRideResponseDto(
    @Json(name = "endRide") val endRideDto: EndRideDto
)

data class ViewRideResponseDto(
    @Json(name = "battery") val battery: Int,
    @Json(name = "distance") val distance: Int,
    @Json(name = "createdAt") val createdAt: Int
)