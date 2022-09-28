package com.internship.move.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng
import com.internship.move.presentation.authentification.register.RegisterFragment
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.presentation.menu.MenuFragment

class InternalStorageManager(
    context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(KEY_APP_PREFERENCES, Context.MODE_PRIVATE)

    fun getIsUserLoggedIn(): Boolean = sharedPreferences.getBoolean(KEY_IS_USER_LOGGED_IN, false)

    fun getHasUserCompletedOnboarding(): Boolean = sharedPreferences.getBoolean(KEY_HAS_USER_COMPLETED_ONBOARDING, false)

    fun setIsUserLoggedIn(isUserLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_USER_LOGGED_IN, isUserLoggedIn).apply()
    }

    fun setUserHasCompletedOnboarding(hasUserCompletedOnboarding: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_HAS_USER_COMPLETED_ONBOARDING, hasUserCompletedOnboarding).apply()
    }

    fun setLoginToken(token: String) {
        sharedPreferences.edit().putString(KEY_SESSION_TOKEN, token).apply()
    }

    fun getLoginToken(): String =
        sharedPreferences.getString(KEY_SESSION_TOKEN, "").toString()

    fun saveUserLocation(position: LatLng) {
        sharedPreferences.edit().putFloat(MapViewModel.KEY_LATITUDE, position.latitude.toFloat()).apply()
        sharedPreferences.edit().putFloat(MapViewModel.KEY_LONGITUDE, position.longitude.toFloat()).apply()
    }

    fun getUserLocation(): LatLng = LatLng(
        sharedPreferences.getFloat(MapViewModel.KEY_LATITUDE, 1f).toDouble(),
        sharedPreferences.getFloat(MapViewModel.KEY_LONGITUDE, 1f).toDouble()
    )

    companion object {
        const val KEY_APP_PREFERENCES = "KEY_APP_PREFERENCES"
        private const val KEY_IS_USER_LOGGED_IN = "KEY_IS_USER_LOGGED_IN"
        private const val KEY_HAS_USER_COMPLETED_ONBOARDING = "KEY_HAS_USER_COMPLETED_ONBOARDING"
        private const val KEY_SESSION_TOKEN = "KEY_SESSION_TOKEN"
    }
}