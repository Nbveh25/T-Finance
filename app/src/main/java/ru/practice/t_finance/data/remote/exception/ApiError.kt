package ru.practice.t_finance.data.remote.exception

data class ApiError(
    val message: String,
    val code: String? = null
)