package com.internship.move.data.dto.scooter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RideRequestDto(
    @Json(name = "_id") val _id: String,
    @Json(name = "latUser") val latUser: Double,
    @Json(name = "longUser") val longUser: Double,
    @Json(name = "scooterNumber") val scooterNumber: Int
)