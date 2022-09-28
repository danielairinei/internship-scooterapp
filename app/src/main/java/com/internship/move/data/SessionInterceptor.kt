package com.internship.move.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class SessionInterceptor(
    private val authenticationTokenProvider: AuthenticationTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val token = authenticationTokenProvider.getAuthenticationToken()
        if (token.isNotEmpty()) {
            builder.header(KEY_HEADER_AUTHORIZATION, KEY_HEADER_TOKEN_PREFIX + token)
        }

        return chain.proceed(builder.build())
    }

    companion object {
        private const val KEY_HEADER_AUTHORIZATION = "Authorization"
        private const val KEY_HEADER_TOKEN_PREFIX = "Bearer "
    }
}