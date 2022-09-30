package com.internship.move.presentation.authentification.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.ErrorResponse
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

    private val _userRegisterData: MutableLiveData<UserRegisterResponseDto> = MutableLiveData()
    val userRegisterData: LiveData<UserRegisterResponseDto>
        get() = _userRegisterData
    private val _userLoginData: MutableLiveData<UserLoginResponseDto> = MutableLiveData()
    val userLoginData: LiveData<UserLoginResponseDto>
        get() = _userLoginData
    private val _errorData: MutableLiveData<ErrorResponse> = MutableLiveData(null)
    val errorData: LiveData<ErrorResponse>
        get() = _errorData
    private val _licenseData: MutableLiveData<String> = MutableLiveData("")
    val licenseData: LiveData<String>
        get() = _licenseData

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun setIsUserLoggedIn(boolean: Boolean){
        repo.setIsUserLoggedIn(boolean)
    }

    fun login(newUserLoginRequestDto: UserLoginRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.loginRequest(newUserLoginRequestDto)
                _userLoginData.postValue(response)
                repo.setLoginToken(response.loginToken)
                repo.setIsUserLoggedIn(true)
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorJsonAdapter))
            }
        }
    }

    fun register(newUserRegisterRequestDto: UserRegisterRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userRegisterData.postValue(repo.registerRequest(newUserRegisterRequestDto))
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorJsonAdapter))
            }
        }
    }

    fun licenseVerification(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.licenseVerification(file)
                _licenseData.postValue(response.drivinglicense)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}