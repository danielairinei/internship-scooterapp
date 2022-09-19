package com.internship.move.presentation.authentification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.user.*
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repo: UserRepository
) : ViewModel() {

    val userRegisterData: MutableLiveData<UserRegisterResponseDto> = MutableLiveData()
    val userLoginData: MutableLiveData<UserLoginResponseDto> = MutableLiveData()
    val errorData: MutableLiveData<ErrorResponseDto> = MutableLiveData(null)

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun login(newUserLoginRequestDto: UserLoginRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userLoginData.postValue(repo.loginRequest(newUserLoginRequestDto))
                repo.setIsUserLoggedIn(true)
            } catch (e: Exception) {
                errorData.postValue(ErrorResponseDto(e.message.toString()))
            }
        }
    }

    fun register(newUserRegisterRequestDto: UserRegisterRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRegisterData.postValue(repo.registerRequest(newUserRegisterRequestDto))
            } catch (e: Exception) {
                errorData.postValue(ErrorResponseDto(e.message.toString()))
            }
        }
    }
}