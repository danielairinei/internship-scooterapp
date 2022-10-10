package com.internship.move.repository

import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.LockUnlockDto
import com.internship.move.data.dto.ride.StartRideRequestDto
import com.internship.move.data.dto.ride.StartRideResponseDto
import com.internship.move.data.dto.ride.UpdateRideRequestDto
import com.internship.move.data.dto.ride.UpdateRideResponseDto
import com.internship.move.data.dto.ride.ViewRideRequestDto
import com.internship.move.data.dto.ride.ViewRideResponseDto
import com.internship.move.networking.service.RideApi
import com.internship.move.utils.InternalStorageManager

class RideRepository(
    private val internalStorageManager: InternalStorageManager,
    private val rideApi: RideApi
) {

    suspend fun lockScooter(lockUnlockDto: LockUnlockDto) {
        rideApi.lockScooter(lockUnlockDto)
    }

    suspend fun unlockScooter(lockUnlockDto: LockUnlockDto) {
        rideApi.unlockScooter(lockUnlockDto)
    }

    suspend fun startRide(rideRequestDto: StartRideRequestDto): StartRideResponseDto = rideApi.startRide(rideRequestDto)

    suspend fun endRide(endRideRequestDto: EndRideRequestDto) {
        rideApi.endRide(endRideRequestDto)
    }

    suspend fun updateRide(updateRideRequestDto: UpdateRideRequestDto): UpdateRideResponseDto = rideApi.updateRide(updateRideRequestDto)

    suspend fun viewRide(viewRideRequestDto: ViewRideRequestDto): ViewRideResponseDto = rideApi.viewRide(viewRideRequestDto)

    fun saveCurrentRideId(scooterId: String) {
        internalStorageManager.saveCurrentRideId(scooterId)
    }

    fun getCurrentRideId(): String = internalStorageManager.getCurrentRideId()
}