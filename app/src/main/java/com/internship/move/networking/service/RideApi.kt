package com.internship.move.networking.service

import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.LockUnlockDto
import com.internship.move.data.dto.ride.StartRideRequestDto
import com.internship.move.data.dto.ride.StartRideResponseDto
import com.internship.move.data.dto.ride.UpdateRideRequestDto
import com.internship.move.data.dto.ride.UpdateRideResponseDto
import com.internship.move.data.dto.ride.ViewRideRequestDto
import com.internship.move.data.dto.ride.ViewRideResponseDto
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface RideApi {

    @PATCH("/scooter/lock")
    suspend fun lockScooter(@Body lockUnlockDto: LockUnlockDto)

    @PATCH("/scooter/unlock")
    suspend fun unlockScooter(@Body lockUnlockDto: LockUnlockDto)

    @POST("/ride/start")
    suspend fun startRide(@Body rideRequestDto: StartRideRequestDto): StartRideResponseDto

    @PATCH("/ride/end")
    suspend fun endRide(@Body endRideRequestDto: EndRideRequestDto)

    @PATCH("/ride/update")
    suspend fun updateRide(@Body updateRideRequestDto: UpdateRideRequestDto): UpdateRideResponseDto

    @PATCH("ride/details")
    suspend fun viewRide(@Body viewRideRequestDto: ViewRideRequestDto): ViewRideResponseDto
}