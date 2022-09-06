package com.internship.move.data.dto.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRegisterRequestDto(
    val name: String,
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class UserLoginRequestDto(
    val email: String,
    val password: String
)