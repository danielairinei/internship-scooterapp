package com.internship.move.presentation.authentification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.user.*
import com.internship.move.repository.UserRepository
import com.internship.move.utils.extensions.toErrorResponse
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AuthenticationViewModel(
    private val repo: UserRepository,
    private val errorJsonAdapter: JsonAdapter<ErrorResponse>
) : ViewModel() {

    val userRegisterData: MutableLiveData<UserRegisterResponseDto> = MutableLiveData()
    val userLoginData: MutableLiveData<UserLoginResponseDto> = MutableLiveData()
    val errorData: MutableLiveData<ErrorResponse> = MutableLiveData(null)
    val licenseData: MutableLiveData<String> = MutableLiveData("")

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun login(newUserLoginRequestDto: UserLoginRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.loginRequest(newUserLoginRequestDto)
                userLoginData.postValue(response)
                repo.setLoginToken(response.loginToken)
            } catch (e: Exception) {
                errorData.postValue(e.toErrorResponse(errorJsonAdapter))
            }
        }
    }

    fun register(newUserRegisterRequestDto: UserRegisterRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRegisterData.postValue(repo.registerRequest(newUserRegisterRequestDto))
            } catch (e: Exception) {
                errorData.postValue(e.toErrorResponse(errorJsonAdapter))
            }
        }
    }

    fun licenseVerification(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.licenseVerification(file, "Bearer ${repo.getLoginToken()}")
                licenseData.postValue(response.drivinglicense)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    companion object {
        const val KEY_SESSION_TOKEN = "KEY_SESSION_TOKEN"
    }
}