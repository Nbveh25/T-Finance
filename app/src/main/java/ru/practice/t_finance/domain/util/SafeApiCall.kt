package ru.practice.t_finance.domain.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> safeApiCall(
    crossinline body: suspend () -> T
): ResponseResult<T> {
    return try {
        val response = withContext(Dispatchers.IO) {
            body()
        }
        ResponseResult.Success(response)
    } catch (e: Exception) {
        ResponseResult.Failure(e)
    }
} 