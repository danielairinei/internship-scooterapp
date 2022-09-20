package com.internship.move.data.dto.user

import retrofit2.http.*

interface UserApi {

    @POST("/auth/register")
    suspend fun registerRequest(@Body userRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto

    @POST("/auth/login")
    suspend fun loginRequest(@Body userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto

    @DELETE("/auth/logout")
    suspend fun logoutRequest(@Header("Authorization") token: String)

    @PUT("/auth/driving-license")
    suspend fun uploadDrivingLicense()
}