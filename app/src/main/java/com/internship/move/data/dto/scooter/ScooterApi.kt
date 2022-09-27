package com.internship.move.data.dto.scooter

import retrofit2.http.*

interface ScooterApi {

    @GET("/scooter")
    suspend fun findScooters(@Query("latitudine") latitude: Double, @Query("longitudine") longitude: Double): List<ScooterDto>

    @GET("/scooter")
    suspend fun getScooterByNumber(@Header("Authorization") scooterNumber: Int): ScooterDto

    @PATCH("/scooter/unlock")
    suspend fun unlockScooter(@Header("Authorization") token: String, @Body scooterId: String)

    @POST("/ride/start")
    suspend fun startRide(@Header("Authorization") token: String, @Body rideRequestDto: RideRequestDto)
}