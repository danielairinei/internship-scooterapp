package com.internship.move.presentation.map.viewmodel

import androidx.lifecycle.ViewModel
import com.internship.move.repository.UserRepository

class MapViewModel(
    private val repo: UserRepository
) : ViewModel() {

    fun logOut() {
        repo.setIsUserLoggedIn(false)
    }

    fun clearApp() {
        repo.setIsUserLoggedIn(false)
        repo.setHasUserCompletedOnboarding(false)
    }
}