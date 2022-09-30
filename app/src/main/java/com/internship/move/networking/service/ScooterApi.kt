package com.internship.move.networking.service

import com.internship.move.data.dto.scooter.ScooterDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ScooterApi {

    @GET("/scooter")
    suspend fun findScooters(@Query("latitudine") latitude: Double, @Query("longitudine") longitude: Double): List<ScooterDto>

    @GET("/scooter")
    suspend fun getScooterByNumber(@Header("Authorization") scooterNumber: Int): ScooterDto
}