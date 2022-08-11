package com.mk.tasky.authentication.data.remote.interceptors

import com.mk.tasky.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build()
        return chain.proceed(request)
    }
}
