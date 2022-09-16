package com.mk.tasky.core.util

import com.mk.tasky.authentication.data.remote.dto.ErrorResponseDto
import com.squareup.moshi.Moshi
import retrofit2.HttpException

fun HttpException.parseError(): String {
    this.response()?.errorBody()?.source()?.let { source ->
        val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponseDto::class.java)
        try {
            val body = moshiAdapter.fromJson(source)
            body?.message?.let {
                return it
            }
        } catch (e: Exception) {
        }
    }
    throw this
}
