package ru.practice.t_finance.data.repository

import android.util.Log
import jakarta.inject.Inject
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.mapper.TransactionMapper
import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.domain.repository.TransactionRepository

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun addTransaction(transactionModel: TransactionModel): Result<Unit> {
        return try {
            when (val response = apiService.addTransaction(TransactionMapper.toRequest(transactionModel))) {

                is NetworkResponse.Success -> {
                    Log.d("TransactionRepositoryImpl", "Success: ${response.data}")
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Log.d("TransactionRepositoryImpl", "EmptySuccess: ${response.code}")
                    Result.success(Unit)
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        400 -> {
                            Log.d("TransactionRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Incorrect Payload"))
                        }
                        401 -> {
                            Log.d("TransactionRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        404 -> {
                            Log.d("TransactionRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неправильный формат даты"))
                        }
                        else -> {
                            Log.d("TransactionRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неизвестная ошибка"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.d("TransactionRepositoryImpl", "Network Error ${response.error}")
                    Result.failure(Exception("Ошибка сети"))
                }

                is NetworkResponse.UnknownError -> {
                    Log.d("TransactionRepositoryImpl", "Unknown Error ${response.error}")
                    Result.failure(Exception("Неизвестная ошибка"))
                }
            }
        } catch (e: Exception) {
            Log.d("TransactionRepositoryImpl", "Network Error ${e.message}")
            return Result.failure(e)
        }
    }

}