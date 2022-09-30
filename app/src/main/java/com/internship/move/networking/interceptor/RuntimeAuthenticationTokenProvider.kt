package com.internship.move.networking.interceptor

import com.internship.move.utils.InternalStorageManager

class RuntimeAuthenticationTokenProvider(
    private val internalStorageManager: InternalStorageManager
) : AuthenticationTokenProvider {

    override fun getAuthenticationToken(): String {
        return internalStorageManager.getLoginToken()
    }
}