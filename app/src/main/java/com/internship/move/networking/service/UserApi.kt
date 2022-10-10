package com.internship.move.networking.service

import com.internship.move.data.dto.user.UserDto
import com.internship.move.data.dto.user.UserLoginRequestDto
import com.internship.move.data.dto.user.UserLoginResponseDto
import com.internship.move.data.dto.user.UserRegisterRequestDto
import com.internship.move.data.dto.user.UserRegisterResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface UserApi {

    @POST("/auth/register")
    suspend fun registerRequest(@Body userRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto

    @POST("/auth/login")
    suspend fun loginRequest(@Body userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto

    @Multipart
    @PUT("/auth/driving-license")
    suspend fun uploadDrivingLicense(@Part image: MultipartBody.Part): UserDto

    @DELETE("/auth/logout")
    suspend fun logoutRequest()

    @GET("/user/currUser")
    suspend fun getUser() : UserDto
}