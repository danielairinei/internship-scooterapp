package com.internship.move.data

interface AuthenticationTokenProvider {

    fun getAuthenticationToken(): String
}