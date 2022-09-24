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

    fun getScooterByNumber(scooterNumber: Int?): ScooterDto? = scooterList.value?.find {
        it.number == scooterNumber
    }
}