package ru.practice.t_finance.domain.util

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure(val error: Throwable) : ResponseResult<Nothing>()
    object Loading : ResponseResult<Nothing>()
}