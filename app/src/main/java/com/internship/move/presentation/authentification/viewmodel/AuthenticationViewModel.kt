package com.internship.move.presentation.authentification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.user.UserLoginRequestDto
import com.internship.move.data.dto.user.UserLoginResponseDto
import com.internship.move.data.dto.user.UserRegisterRequestDto
import com.internship.move.data.dto.user.UserRegisterResponseDto
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repo: UserRepository
) : ViewModel() {

    val userRegisterData: MutableLiveData<UserRegisterResponseDto> = MutableLiveData()
    val userLoginData: MutableLiveData<UserLoginResponseDto> = MutableLiveData()

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userLoginData.value = repo.loginRequest(UserLoginRequestDto(email, password))
                repo.setIsUserLoggedIn(true)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun register(newUserRegisterRequestDto: UserRegisterRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.registerRequest(newUserRegisterRequestDto)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

}