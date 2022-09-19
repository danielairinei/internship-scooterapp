package com.internship.move.repository

import com.internship.move.data.dto.user.UserApi
import com.internship.move.data.dto.user.UserLoginRequestDto
import com.internship.move.data.dto.user.UserLoginResponseDto
import com.internship.move.data.dto.user.UserRegisterRequestDto
import com.internship.move.utils.InternalStorageManager

class UserRepository(
    private val internalStorageManager: InternalStorageManager,
    private val userApi: UserApi
) {

    fun getIsUserLoggedIn() = internalStorageManager.getIsUserLoggedIn()

    fun getHasUserCompletedOnboarding() = internalStorageManager.getHasUserCompletedOnboarding()

    fun setHasUserCompletedOnboarding(boolean: Boolean) {
        internalStorageManager.setUserHasCompletedOnboarding(boolean)
    }

    fun setIsUserLoggedIn(boolean: Boolean) {
        internalStorageManager.setIsUserLoggedIn(boolean)
    }

    suspend fun loginRequest(newLoginRequestDto: UserLoginRequestDto) : UserLoginResponseDto {
        return userApi.loginRequest(newLoginRequestDto)
    }

    suspend fun registerRequest(newRegisterRequestDto: UserRegisterRequestDto) {
        userApi.registerRequest(newRegisterRequestDto)
    }
}