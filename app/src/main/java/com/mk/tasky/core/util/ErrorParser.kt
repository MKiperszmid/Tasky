package com.mk.tasky.core.util

import com.mk.tasky.authentication.data.remote.dto.ErrorResponseDto
import com.mk.tasky.core.data.remote.exceptions.TaskyException
import com.squareup.moshi.Moshi
import retrofit2.HttpException

object ErrorParser {
    fun <T> parseError(e: HttpException): Result<T> {
        e.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponseDto::class.java)
            try {
                val body = moshiAdapter.fromJson(it)
                body?.message?.let { errorMessage ->
                    return Result.failure(TaskyException(errorMessage))
                }
            } catch (e: Exception) {
            }
        }
        return Result.failure(e)
    }
}