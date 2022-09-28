package com.internship.move.data.dto.user

import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @POST("/auth/register")
    suspend fun registerRequest(@Body userRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto

    @POST("/auth/login")
    suspend fun loginRequest(@Body userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto

    @DELETE("/auth/logout")
    suspend fun logoutRequest()

    @Multipart
    @PUT("/auth/driving-license")
    suspend fun uploadDrivingLicense(@Part image: MultipartBody.Part): UserDto
}