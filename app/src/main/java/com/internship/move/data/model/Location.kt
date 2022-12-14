package com.internship.move.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "coordinates") val coordinates: List<Double>,
    @Json(name = "type") val type: String
)