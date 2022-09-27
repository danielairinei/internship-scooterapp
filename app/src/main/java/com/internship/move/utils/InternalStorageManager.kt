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

    fun getIsUserLoggedIn(): Boolean = sharedPreferences.getBoolean(MenuFragment.KEY_IS_USER_LOGGED_IN, false)

    fun getHasUserCompletedOnboarding(): Boolean = sharedPreferences.getBoolean(RegisterFragment.KEY_HAS_USER_COMPLETED_ONBOARDING, false)


    fun setIsUserLoggedIn(isUserLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(MenuFragment.KEY_IS_USER_LOGGED_IN, isUserLoggedIn).apply()
    }

    fun setUserHasCompletedOnboarding(hasUserCompletedOnboarding: Boolean) {
        sharedPreferences.edit().putBoolean(RegisterFragment.KEY_HAS_USER_COMPLETED_ONBOARDING, hasUserCompletedOnboarding).apply()
    }

    fun setLoginToken(token: String) {
        sharedPreferences.edit().putString(AuthenticationViewModel.KEY_SESSION_TOKEN, token).apply()
    }

    fun getLoginToken(): String =
        sharedPreferences.getString(AuthenticationViewModel.KEY_SESSION_TOKEN, "").toString()

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
    }
}