package com.internship.move.presentation.menu.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.user.UserDto
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repo: UserRepository
) : ViewModel() {

    val loggedOut: MutableLiveData<Boolean> = MutableLiveData(false)
    val userData: MutableLiveData<UserDto> = MutableLiveData()

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

    fun getUserRequest(authToken: String) {
        viewModelScope.launch {
            try {
                userData.postValue(repo.getUserRequest(authToken))
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun getLoginToken(): String {
        return repo.getLoginToken()
    }
}