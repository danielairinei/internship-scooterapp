package com.internship.move.networking.interceptor

interface AuthenticationTokenProvider {

    fun getAuthenticationToken(): String
}