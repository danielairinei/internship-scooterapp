package com.internship.move.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.internship.move.repository.UserRepository

class SplashViewModel(
    private val repo: UserRepository
) : ViewModel() {

    fun getIsUserLoggedIn() = repo.getIsUserLoggedIn()

    fun getHasUserCompletedOnboarding() = repo.getHasUserCompletedOnboarding()
}