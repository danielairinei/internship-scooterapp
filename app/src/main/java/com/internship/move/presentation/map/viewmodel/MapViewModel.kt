package com.internship.move.presentation.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.internship.move.data.dto.ErrorResponse
import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.LockUnlockDto
import com.internship.move.data.dto.ride.StartRideRequestDto
import com.internship.move.data.dto.ride.UpdateRideRequestDto
import com.internship.move.data.dto.ride.UpdateRideResponseDto
import com.internship.move.data.dto.ride.ViewRideRequestDto
import com.internship.move.data.dto.ride.ViewRideResponseDto
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
    val errorData: LiveData<ErrorResponse?>
        get() = _errorData

    private val _unlockData: MutableLiveData<Boolean?> = MutableLiveData()
    val unlockData: LiveData<Boolean?>
        get() = _unlockData

    private val _startRideData: MutableLiveData<ScooterDto?> = MutableLiveData()
    val startRideData: LiveData<ScooterDto?>
        get() = _startRideData

    private val _updateRideData: MutableLiveData<UpdateRideResponseDto?> = MutableLiveData()
    val updateRideData: LiveData<UpdateRideResponseDto?>
        get() = _updateRideData

    private val _viewRideData: MutableLiveData<ViewRideResponseDto?> = MutableLiveData()
    val viewRideData: LiveData<ViewRideResponseDto?>
        get() = _viewRideData

    private val _rideInProgress: MutableLiveData<Boolean> = MutableLiveData()
    val rideInProgress: LiveData<Boolean>
        get() = _rideInProgress

    fun findScooters(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _scooterList.postValue(scooterRepo.findScooters(latitude, longitude))
                _errorData.postValue(null)
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

    fun unlockScooter(scooterId: String) {
        viewModelScope.launch {
            try {
                rideRepo.unlockScooter(LockUnlockDto(scooterId))
                _unlockData.postValue(true)
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun lockScooter(scooterId: String) {
        viewModelScope.launch {
            try {
                rideRepo.lockScooter(LockUnlockDto(scooterId))
                _unlockData.postValue(false)
            } catch (e: Exception) {
                Log.e("ERROR", e.toErrorResponse(errorResponseJsonAdapter).toString())
            }
        }
    }

    fun startRide(scooter: ScooterDto?, userLocation: LatLng) {
        viewModelScope.launch {
            try {
                if (scooter != null) {
                    val response = rideRepo.startRide(
                        StartRideRequestDto(
                            scooter.id,
                            userLocation.latitude,
                            userLocation.longitude,
                            scooter.number
                        )
                    )
                    _startRideData.postValue(scooter)
                    saveCurrentRideId(response.updateRideDto.rideId)
                    _errorData.postValue(null)
                    _rideInProgress.postValue(true)
                    _unlockData.postValue(null)
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
                _startRideData.postValue(null)
                _updateRideData.postValue(null)
                _rideInProgress.postValue(false)
                _viewRideData.postValue(null)
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun updateRide(updateRideRequestDto: UpdateRideRequestDto) {
        viewModelScope.launch {
            try {
                val response = rideRepo.updateRide(updateRideRequestDto)
                _updateRideData.postValue(response)
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun viewRide(viewRideRequestDto: ViewRideRequestDto) {
        viewModelScope.launch {
            try {
                _viewRideData.postValue(rideRepo.viewRide(viewRideRequestDto))
            } catch (e: Exception) {
                println(e.toString())
                //_errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun saveUserLocation(position: LatLng) {
        userRepo.saveUserLocation(position)
    }

    fun getCurrentRideId(): String = rideRepo.getCurrentRideId()

    private fun saveCurrentRideId(rideId: String) {
        rideRepo.saveCurrentRideId(rideId)
    }

    fun getUserLocation(): LatLng = userRepo.getUserLocation()
}