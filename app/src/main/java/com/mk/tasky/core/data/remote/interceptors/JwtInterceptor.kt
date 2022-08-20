package com.mk.tasky.core.data.remote.interceptors

import com.mk.tasky.core.domain.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val preferences: Preferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.loadLoggedUser()?.token
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(request.build())
    }
}
