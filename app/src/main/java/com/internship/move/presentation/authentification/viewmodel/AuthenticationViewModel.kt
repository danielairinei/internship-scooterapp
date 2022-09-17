package com.internship.move.presentation.authentification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.user.UserLoginRequestDto
import com.internship.move.data.dto.user.UserRegisterRequestDto
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repo: UserRepository
) : ViewModel() {

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.loginRequest(UserLoginRequestDto(email, password))
            }catch(e : Exception){
                println(e.message)
            }
        }
        repo.setIsUserLoggedIn(true)
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