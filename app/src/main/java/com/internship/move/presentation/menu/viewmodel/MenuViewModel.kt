package com.internship.move.presentation.menu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.data.dto.ErrorResponse
import com.internship.move.data.dto.user.UserDto
import com.internship.move.repository.UserRepository
import com.internship.move.utils.extensions.toErrorResponse
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repo: UserRepository,
    private val errorResponseJsonAdapter: JsonAdapter<ErrorResponse>
) : ViewModel() {

    private val _userData: MutableLiveData<UserDto> = MutableLiveData()
    val userData: LiveData<UserDto>
        get() = _userData
    private val _loggedOut: MutableLiveData<Boolean> = MutableLiveData(false)
    val loggedOut: LiveData<Boolean>
        get() = _loggedOut
    private val _errorData: MutableLiveData<ErrorResponse> = MutableLiveData()
    val errorData: LiveData<ErrorResponse>
        get() = _errorData

    fun logout() {
        viewModelScope.launch(IO) {
            try {
                repo.logoutRequest()
                repo.setIsUserLoggedIn(false)
                _loggedOut.postValue(true)
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun getUserRequest() {
        viewModelScope.launch {
            try {
                _userData.postValue(repo.getUserRequest())
            } catch (e: Exception) {
                _errorData.postValue(e.toErrorResponse(errorResponseJsonAdapter))
            }
        }
    }

    fun getLoginToken(): String = repo.getLoginToken()
}