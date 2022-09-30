package com.internship.move.repository

import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.StartRideRequestDto
import com.internship.move.networking.service.RideApi
import com.internship.move.utils.InternalStorageManager

class RideRepository(
    private val internalStorageManager: InternalStorageManager,
    private val rideApi: RideApi
) {

    suspend fun lockScooter(scooterId: String) {
        rideApi.lockScooter(scooterId)
    }

    suspend fun unlockScooter(scooterId: String) {
        rideApi.unlockScooter(scooterId)
    }

    suspend fun startRide(rideRequestDto: StartRideRequestDto) {
        rideApi.startRide(rideRequestDto)
    }

    suspend fun endRide(endRideRequestDto: EndRideRequestDto) {
        rideApi.endRide(endRideRequestDto)
    }

    fun saveCurrentScooterInRideId(scooterId: String) {
        internalStorageManager.saveCurrentScooterInRideId(scooterId)
    }

    fun getCurrentScooterInRideId(): String = internalStorageManager.getCurrentScooterInRideId()
}