package com.internship.move.presentation.menu.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repo: UserRepository
) : ViewModel() {

    val loggedOut: MutableLiveData<Boolean> = MutableLiveData(false)

    fun logout(logoutToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.logoutRequest("Bearer $logoutToken")
                repo.setIsUserLoggedIn(false)
                loggedOut.postValue(true)
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun clearApp() {
        repo.setIsUserLoggedIn(false)
        repo.setHasUserCompletedOnboarding(false)
    }

    fun getLoginToken(): String {
        return repo.getLoginToken()
    }
}