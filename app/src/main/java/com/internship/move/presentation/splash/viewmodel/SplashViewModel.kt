package com.internship.move.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.internship.move.repository.Repository

class SplashViewModel(
    private val repo: Repository
) : ViewModel() {

    fun getIsUserLoggedIn() = repo.getIsUserLoggedIn()

    fun getHasUserCompletedOnboarding() = repo.getHasUserCompletedOnboarding()
}