package com.createfuture.takehome.data.remote

import com.createfuture.takehome.data.util.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.text.isNullOrBlank

class HeaderInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()

        val requestBuilder = originalRequest.newBuilder()
        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}