package com.internship.move.repository

import com.internship.move.data.dto.user.UserApi
import com.internship.move.data.dto.user.UserDto
import com.internship.move.data.dto.user.UserLoginRequestDto
import com.internship.move.data.dto.user.UserLoginResponseDto
import com.internship.move.data.dto.user.UserRegisterRequestDto
import com.internship.move.data.dto.user.UserRegisterResponseDto
import com.google.android.gms.maps.model.LatLng
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

    fun getLoginToken(): String =
        internalStorageManager.getLoginToken()

    fun saveUserLocation(position: LatLng) {
        internalStorageManager.saveUserLocation(position)
    }

    fun getUserLocation(): LatLng = internalStorageManager.getUserLocation()

    suspend fun loginRequest(newLoginRequestDto: UserLoginRequestDto): UserLoginResponseDto =
        userApi.loginRequest(newLoginRequestDto)


    suspend fun registerRequest(newRegisterRequestDto: UserRegisterRequestDto): UserRegisterResponseDto =
        userApi.registerRequest(newRegisterRequestDto)

    suspend fun licenseVerification(file: File): UserDto = userApi.uploadDrivingLicense(
            image = MultipartBody.Part.createFormData(
                KEY_DRIVING_LICENSE_TAG,
                file.name,
                file.asRequestBody(KEY_DRIVING_LICENSE_TYPE.toMediaTypeOrNull())
            )
        )

    suspend fun logoutRequest() {
        userApi.logoutRequest()
    }

    suspend fun getUserRequest(): UserDto = userApi.getUser()

    companion object {
        private const val KEY_DRIVING_LICENSE_TAG = "drivinglicense"
        private const val KEY_DRIVING_LICENSE_TYPE = "image/jpg"
    }
}