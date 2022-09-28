package com.internship.move.data

import com.internship.move.utils.InternalStorageManager

class RuntimeAuthenticationTokenProvider(
    private val internalStorageManager: InternalStorageManager
) : AuthenticationTokenProvider {

    override fun getAuthenticationToken(): String {
        return internalStorageManager.getLoginToken()
    }
}