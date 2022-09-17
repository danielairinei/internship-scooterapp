package com.internship.move.data.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRegisterResponseDto(
    @Json(name = "user") val user: UserDto
)

@JsonClass(generateAdapter = true)
data class UserLoginResponseDto(
    val userDto: UserDto,
    val loginToken: String
)

@JsonClass(generateAdapter = true)
data class UserErrorResponseDto(
    val message: String
)