package com.internship.move.data.dto

import com.internship.move.data.model.User
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("/user/users")
    suspend fun getUsers(): List<User>

    @POST("/user/register")
    suspend fun register(@Body user: User): User

    @POST("/user/login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String)
}