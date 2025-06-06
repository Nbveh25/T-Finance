package ru.practice.t_finance.data.repository

import android.util.Log
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.request.SendInitialBudgetRequest
import ru.practice.t_finance.domain.repository.InitialBudgetRepository
import javax.inject.Inject

class InitialBudgetRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : InitialBudgetRepository {
    override suspend fun sendInitialBudget(data: String, day: String): Result<Unit> {
        val data = SendInitialBudgetRequest(
            amount = data,
            day = day
        )
        return try {
            when (val response = apiService.sendBudget(data)) {
                is NetworkResponse.Success -> {
                    Result.success(response.data)
                }

                // 204
                is NetworkResponse.EmptySuccess -> {
                    Result.success(Unit)
                }

                is NetworkResponse.ApiError -> {
                    when (response.code) {
                        400 -> {
                            Result.failure(Exception("Неправильное значение"))
                        }

                        401 -> {
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }

                        else -> {
                            Result.failure(Exception("Unknown API error"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Result.failure(response.error)
                }

                is NetworkResponse.UnknownError -> {
                    Result.failure(response.error)
                }
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

}