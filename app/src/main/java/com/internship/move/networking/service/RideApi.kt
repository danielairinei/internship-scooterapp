package com.internship.move.networking.service

import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.StartRideRequestDto
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface RideApi {

    @PATCH("/scooter/lock")
    suspend fun lockScooter(@Body scooterId: String)

    @PATCH("/scooter/unlock")
    suspend fun unlockScooter(@Body scooterId: String)

    @POST("/ride/start")
    suspend fun startRide(@Body rideRequestDto: StartRideRequestDto)

    @PATCH("/ride/end")
    suspend fun endRide(@Body endRideRequestDto: EndRideRequestDto)
}