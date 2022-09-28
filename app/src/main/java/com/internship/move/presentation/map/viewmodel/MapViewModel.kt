package com.internship.move.presentation.map.viewmodel

import androidx.lifecycle.LiveData
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

    private val _scooterList: MutableLiveData<List<ScooterDto>> = MutableLiveData()
    val scooterList: LiveData<List<ScooterDto>>
        get() = _scooterList
    private val _errorData: MutableLiveData<ErrorResponse> = MutableLiveData()
    val errorData: LiveData<ErrorResponse>
        get() = _errorData

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
}