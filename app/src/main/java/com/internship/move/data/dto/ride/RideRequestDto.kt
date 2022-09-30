package com.internship.move.data.dto.ride

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StartRideRequestDto(
    @Json(name = "idScooter") val id: String,
    @Json(name = "latUser") val latUser: Double,
    @Json(name = "longUser") val longUser: Double,
    @Json(name = "scooterNumber") val scooterNumber: Int
)

@JsonClass(generateAdapter = true)
data class EndRideRequestDto(
    @Json(name = "idScooter") val rideId: String,
    @Json(name = "longitude") val long: Double,
    @Json(name = "latitude") val lat: Double
)