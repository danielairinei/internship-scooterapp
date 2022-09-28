package com.internship.move.data.dto.user

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import okhttp3.MultipartBody

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