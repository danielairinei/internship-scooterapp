package com.internship.move.data.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRegisterRequestDto(
    @Json(name = "email") val email: String,
    @Json(name = "name") val name: String,
    @Json(name = "password") val password: String
)

@JsonClass(generateAdapter = true)
data class UserLoginRequestDto(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)