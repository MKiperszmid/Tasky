package com.mk.tasky.core.data.util

import com.mk.tasky.core.data.remote.exceptions.NetworkErrorException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

// https://www.droidcon.com/2022/04/06/resilient-use-cases-with-kotlin-result-coroutines-and-annotations/
inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: HttpException) {
        val errorMessage = e.parseError()
        Result.failure(NetworkErrorException(errorMessage))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
