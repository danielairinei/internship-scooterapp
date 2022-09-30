package com.internship.move.presentation.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.internship.move.data.dto.ErrorResponse
import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.StartRideRequestDto
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.presentation.map.adapter.ScooterPlace
import com.internship.move.repository.RideRepository
import com.internship.move.repository.ScooterRepository
import com.internship.move.repository.UserRepository
import com.internship.move.utils.extensions.toErrorResponse
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.launch

class MapViewModel(
    private val userRepo: UserRepository,
    private val scooterRepo: ScooterRepository,
    private val rideRepo: RideRepository,
    private val errorResponseJsonAdapter: JsonAdapter<ErrorResponse>
) : ViewModel() {

    private val _scooterList: MutableLiveData<List<ScooterDto>> = MutableLiveData()
    val scooterList: LiveData<List<ScooterDto>>
        get() = _scooterList
    private val _errorData: MutableLiveData<ErrorResponse?> = MutableLiveData()
    val errorData: MutableLiveData<ErrorResponse?>
        get() = _errorData
    private val _rideData: MutableLiveData<ScooterDto?> = MutableLiveData()
    val rideData: LiveData<ScooterDto?>
        get() = _rideData
    val fakeRide: MutableLiveData<ScooterDto?> = MutableLiveData()

    fun findScooters(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _scooterList.postValue(scooterRepo.findScooters(latitude, longitude))
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
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
                        scooter.id
                    )
                )
            }
        }
        return markers
    }

    fun getScooterByNumber(scooterNumber: Int?): ScooterDto? = scooterList.value?.find {
        it.number == scooterNumber
    }

    fun unlockScooter(scooter: ScooterDto) {
        viewModelScope.launch {
            try {
                rideRepo.unlockScooter(scooter.id)
//                rideData.postValue(scooter)
            } catch (e: Exception) {
                Log.e("ERROR", e.toErrorResponse(errorResponseJsonAdapter).toString())
            }
        }
    }

    fun lockScooter(scooterDto: ScooterDto) {
        viewModelScope.launch {
            try {
                rideRepo.lockScooter(scooterDto.id)
            } catch (e: Exception) {
                Log.e("ERROR", e.toErrorResponse(errorResponseJsonAdapter).toString())
            }
        }
    }

    fun startRide(scooter: ScooterDto?, userLocation: LatLng) {
        viewModelScope.launch {
            try {
                if (scooter != null) {
                    rideRepo.startRide(
                        StartRideRequestDto(
                            scooter.id,
                            userLocation.latitude,
                            userLocation.longitude,
                            scooter.number
                        )
                    )
                    rideRepo.saveCurrentScooterInRideId(scooter.id)
                    _rideData.postValue(scooter)
                    _errorData.postValue(null)
                }
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun endRide(endRideRequestDto: EndRideRequestDto) {
        viewModelScope.launch {
            try {
                rideRepo.endRide(endRideRequestDto)
                _rideData.postValue(null)
            } catch (e: Exception) {
                Log.e("ERROR", e.toErrorResponse(errorResponseJsonAdapter).toString())
            }
        }
    }

    fun saveUserLocation(position: LatLng) {
        userRepo.saveUserLocation(position)
    }

    fun getCurrentScooterInRideId(): String = rideRepo.getCurrentScooterInRideId()

    fun getUserLocation(): LatLng = userRepo.getUserLocation()

    fun fakeStartRide(scooterDto: ScooterDto?){
        fakeRide.postValue(scooterDto)
    }
}