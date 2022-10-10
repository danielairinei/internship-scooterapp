package com.internship.move.data.dto.ride

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LockUnlockDto(
    @Json(name = "_id") val id: String
)