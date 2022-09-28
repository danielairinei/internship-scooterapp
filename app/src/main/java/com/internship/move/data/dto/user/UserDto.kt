package com.internship.move.data.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "_id") val id: String,
    @Json(name = "drivinglicense") val drivinglicense: String = "",
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String,
    @Json(name = "password") val password: String,
    @Json(name = "validated") val validated: Boolean = true
)