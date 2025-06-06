package ru.practice.t_finance.data.remote.handler

sealed class NetworkResponse<out T, out E> {
    data class Success<T>(val data: T) : NetworkResponse<T, Nothing>()
    data class EmptySuccess<T>(val code: Int) : NetworkResponse<T, Nothing>()
    data class ApiError<E>(val code: Int, val body: E?) : NetworkResponse<Nothing, E>()
    data class NetworkError(val error: Throwable) : NetworkResponse<Nothing, Nothing>()
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing, Nothing>()
}