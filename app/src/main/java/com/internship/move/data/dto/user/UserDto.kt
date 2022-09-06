package com.internship.move.data.dto.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val email: String,
    val name: String,
    val password: String,
    val id: String,
    val drivingLicense: String,
    val validated: Boolean
)