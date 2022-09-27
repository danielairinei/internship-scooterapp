package com.internship.move.presentation.map.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.internship.move.data.dto.ErrorResponse
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.presentation.map.adapter.ScooterPlace
import com.internship.move.repository.ScooterRepository
import com.internship.move.repository.UserRepository
import com.internship.move.utils.extensions.toErrorResponse
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.launch

class MapViewModel(
    private val userRepo: UserRepository,
    private val scooterRepo: ScooterRepository,
    private val errorResponseJsonAdapter: JsonAdapter<ErrorResponse>
) : ViewModel() {

    val scooterList: MutableLiveData<List<ScooterDto>> = MutableLiveData()
    val rideStarted: MutableLiveData<Boolean> = MutableLiveData(false)

    fun findScooters(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                scooterList.postValue(scooterRepo.findScooters(latitude, longitude))
            } catch (e: Exception) {
                Log.e("Find scooter :", e.toErrorResponse(errorResponseJsonAdapter).toString())
            }
        }
    }

    fun getMarkerList(currentList: List<ScooterDto>): MutableList<ScooterPlace> {
        val markers = mutableListOf<ScooterPlace>().apply {
            currentList.forEach { scooter ->
                add(
                    ScooterPlace(
                        LatLng(scooter.location.coordinates[1], scooter.location.coordinates[0]),
                        scooter.number.toString(),
                        scooter._id
                    )
                )
            }
        }
        return markers
    }

    fun simulateRide() {
        rideStarted.postValue(true)
    }

    fun endRide() {
        rideStarted.postValue(false)
    }

    fun getScooterByNumber(scooterNumber: Int?): ScooterDto? = scooterList.value?.find {
        it.number == scooterNumber
    }

//    fun unlockScooter(token: String, scooter: ScooterDto) {
//        viewModelScope.launch {
//            try {
//                scooterRepo.unlockScooter("Bearer $token", scooter._id)
//                unlockedScooter.postValue(scooter)
//            } catch (e: Exception) {
//                Log.e("ERROR", e.toErrorResponse(errorResponseJsonAdapter).toString())
//            }
//        }
//    }

    ////START RIDE
//    fun startRide(scooter: ScooterDto?, userLocation: LatLng, token: String) {
//        viewModelScope.launch {
//            try {
//                if (scooter != null) {
//                    scooterRepo.startRide(
//                        "Bearer $token",
//                        RideRequestDto(
//                            scooter._id,
//                            userLocation.latitude,
//                            userLocation.longitude,
//                            scooter.number
//                        )
//                    )
//                    rideStarted.postValue(scooter)
//                }
//            } catch (e: Exception) {
//                Log.e("ERROR", e.toErrorResponse(errorResponseJsonAdapter).toString())
//            }
//        }
//    }

    fun getLoginToken(): String = userRepo.getLoginToken()

    fun saveUserLocation(position: LatLng) {
        userRepo.saveUserLocation(position)
    }

    fun getUserLocation(): LatLng = userRepo.getUserLocation()

    companion object {
        const val KEY_LATITUDE = "KEY_LATITUDE"
        const val KEY_LONGITUDE = "KEY_LONGITUDE"
    }
}