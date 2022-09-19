package com.internship.move.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email") val email: String,
    @Json(name = "name")val name: String,
    @Json(name = "password")val password: String
)