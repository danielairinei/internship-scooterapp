package com.internship.move

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.internship.move.feature.authentification.register.RegisterFragment
import com.internship.move.feature.map.MapFragment

open class MainViewModel : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        MainActivity.appContext.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    fun getIsUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(MapFragment.IS_USER_LOGGED_IN, false)
    }

    fun getHasUserCompletedOnboarding(): Boolean {
        return sharedPreferences.getBoolean(RegisterFragment.HAS_USER_COMPLETED_ONBOARDING_KEY, false)
    }

    fun setIsUserLoggedIn(isUserLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(MapFragment.IS_USER_LOGGED_IN, isUserLoggedIn).apply()
    }

    fun setUserHasCompletedOnboarding(hasUserCompletedOnboarding: Boolean) {
        sharedPreferences.edit().putBoolean(RegisterFragment.HAS_USER_COMPLETED_ONBOARDING_KEY, hasUserCompletedOnboarding).apply()
    }

    companion object {
        const val PREFERENCES_KEY = "PREFERENCES_KEY"
    }
}