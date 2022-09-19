package com.internship.move.presentation.menu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.move.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repo: UserRepository
) : ViewModel() {

    fun logout(logoutToken : String){
        repo.setIsUserLoggedIn(false)
//        viewModelScope.launch(Dispatchers.IO){
//            try{
//                repo.logoutRequest(logoutToken)
//            }catch (e : Exception){
//                println(e.message)
//            }
//        }
    }

    fun clearApp() {
        repo.setIsUserLoggedIn(false)
        repo.setHasUserCompletedOnboarding(false)
    }
}