package com.internship.move.repository

import com.internship.move.data.dto.scooter.RideRequestDto
import com.internship.move.data.dto.scooter.ScooterApi
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.utils.InternalStorageManager

class ScooterRepository(
    private val internalStorageManager: InternalStorageManager,
    private val scooterApi: ScooterApi
) {

    suspend fun findScooters(latitude: Double, longitude: Double): List<ScooterDto> = scooterApi.findScooters(latitude, longitude)

    suspend fun unlockScooter(token: String, scooterId: String) {
        scooterApi.unlockScooter(scooterId)
    }

    suspend fun startRide(rideRequestDto: RideRequestDto) {
        scooterApi.startRide(rideRequestDto)
    }

    suspend fun getScooterByNumber(scooterNumber: Int): ScooterDto = scooterApi.getScooterByNumber(scooterNumber)
}