package com.internship.move.data.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRegisterResponseDto(
    @Json(name = "user") val user: UserDto
)

@JsonClass(generateAdapter = true)
data class UserLoginResponseDto(
    @Json(name = "user") val userDto: UserDto,
    @Json(name = "token") val loginToken: String
)

class UserLogoutResponseDto


@JsonClass(generateAdapter = true)
data class ErrorResponseDto(
    @Json(name = "message") val message: String
)