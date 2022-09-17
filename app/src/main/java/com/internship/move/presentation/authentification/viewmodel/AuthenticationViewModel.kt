package com.internship.move.presentation.authentification.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.model.User
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repo: UserRepository
) : ViewModel() {

    fun notifyUserHasCompletedOnboarding() {
        repo.setHasUserCompletedOnboarding(true)
    }

    fun login(email: String, password: String) {
        repo.setIsUserLoggedIn(true)
    }

    fun register(newUser : User) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repo.register(newUser)
            }catch(e: Exception){
                e.message?.let { Log.e(TAG, it) }
            }
        }
    }
}