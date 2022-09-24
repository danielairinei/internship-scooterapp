package com.internship.move.data.dto.scooter

import com.internship.move.data.model.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScooterDto(
    @Json(name = "__v") val __v: Int,
    @Json(name = "_id") val _id: String,
    @Json(name = "battery") val battery: Int,
    @Json(name = "bookStatus") val bookStatus: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "location") val location: Location,
    @Json(name = "lockedStatus") val lockedStatus: Boolean,
    @Json(name = "number") val number: Int,
    @Json(name = "updatedAt") val updatedAt: String
)