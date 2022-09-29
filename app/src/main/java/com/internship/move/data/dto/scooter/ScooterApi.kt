package com.internship.move.data.dto.scooter

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ScooterApi {

    @GET("/scooter")
    suspend fun findScooters(@Query("latitudine") latitude: Double, @Query("longitudine") longitude: Double): List<ScooterDto>

    @GET("/scooter")
    suspend fun getScooterByNumber(@Header("Authorization") scooterNumber: Int): ScooterDto

    @PATCH("/scooter/unlock")
    suspend fun unlockScooter(@Body scooterId: String)

    @POST("/ride/start")
    suspend fun startRide(@Body rideRequestDto: RideRequestDto)

    @PATCH("/ride/end")
    suspend fun endRide()
}