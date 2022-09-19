package com.internship.move.data.dto.user

import com.internship.move.data.model.User
import retrofit2.http.*

interface UserApi {

    @GET("/auth/users")
    suspend fun getUsers(): List<User>

    @POST("/auth/register")
    suspend fun registerRequest(@Body userRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto

    @POST("/auth/login")
    suspend fun loginRequest(@Body userLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto

    @DELETE("/auth/logout")
    suspend fun logoutRequest(@Field("token") token: String) : UserLogoutResponseDto
}