package com.internship.move.presentation.authentification.viewmodel

import androidx.lifecycle.ViewModel
import com.internship.move.repository.Repository

class AuthenticationViewModel(
    private val repo: Repository
) : ViewModel() {

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun login(email: String, password: String) {
        repo.setIsUserLoggedIn(true)
    }

    fun register(email: String, username: String, password: String) {
        repo.setIsUserLoggedIn(true)
    }
}