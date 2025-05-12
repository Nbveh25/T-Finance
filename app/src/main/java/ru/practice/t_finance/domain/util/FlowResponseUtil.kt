package ru.practice.t_finance.domain.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T> flowResponse(
    crossinline body: suspend () -> ResponseResult<T>
): Flow<ResponseResult<T>> = flow {
    emit(ResponseResult.Loading)
    emit(body())
}.flowOn(Dispatchers.IO)