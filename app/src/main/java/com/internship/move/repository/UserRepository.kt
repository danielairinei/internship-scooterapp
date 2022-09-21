package com.internship.move.repository

import com.internship.move.data.dto.user.*
import com.internship.move.utils.InternalStorageManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

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

    fun getLoginToken(): String {
        return internalStorageManager.getLoginToken()
    }

    suspend fun loginRequest(newLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto {
        return userApi.loginRequest(newLoginRequestDto)
    }

    suspend fun registerRequest(newRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto {
        return userApi.registerRequest(newRegisterRequestDto)
    }

    suspend fun licenseVerification(file: File, token: String): UserDto {
        return userApi.uploadDrivingLicense(
            token,
            image = MultipartBody.Part.createFormData(
                "driving_license",
                file.name,
                file.asRequestBody("image/jpg".toMediaTypeOrNull())
            )
        )
    }

    suspend fun logoutRequest(logoutToken: String) {
        userApi.logoutRequest(logoutToken)
    }
}