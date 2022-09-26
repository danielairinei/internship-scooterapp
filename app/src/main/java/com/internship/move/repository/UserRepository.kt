package com.internship.move.repository

import com.internship.move.data.dto.user.*
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

    fun setLoginToken(token: String) {
        internalStorageManager.setLoginToken(token)
    }

    fun getLoginToken(): String =
        internalStorageManager.getLoginToken()


    suspend fun loginRequest(newLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto =
        userApi.loginRequest(newLoginRequestDto)


    suspend fun registerRequest(newRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto =
        userApi.registerRequest(newRegisterRequestDto)


    suspend fun logoutRequest(logoutToken: String) {
        userApi.logoutRequest(logoutToken)
    }

    suspend fun getUserRequest(authToken: String): UserDto = userApi.getUser(authToken)

}