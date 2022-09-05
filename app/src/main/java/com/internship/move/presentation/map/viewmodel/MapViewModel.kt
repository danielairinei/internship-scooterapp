package com.internship.move.presentation.map.viewmodel

import androidx.lifecycle.ViewModel
import com.internship.move.repository.Repository

class MapViewModel(
    private val repo: Repository
) : ViewModel() {

    fun logOut() {
        repo.setIsUserLoggedIn(false)
    }

    fun clearApp() {
        repo.setIsUserLoggedIn(false)
        repo.setHasUserCompletedOnboarding(false)
    }
}