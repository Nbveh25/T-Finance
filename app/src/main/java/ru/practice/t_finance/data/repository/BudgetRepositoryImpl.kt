package ru.practice.t_finance.data.repository

import android.util.Log
import jakarta.inject.Inject
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.mapper.BudgetMapper
import ru.practice.t_finance.domain.model.BudgetModel
import ru.practice.t_finance.domain.repository.BudgetRepository

class BudgetRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): BudgetRepository {
    override suspend fun getBudget(): Result<BudgetModel> {
        return try {
            when(val response = apiService.getBudget()) {
                is NetworkResponse.Success -> {
                    Log.d("BudgetRepositoryImpl", "Success")
                    Result.success(BudgetMapper.toModel(response.data))
                }

                is NetworkResponse.EmptySuccess -> {
                    Log.d("BudgetRepositoryImpl", "Empty Response")
                    Result.failure(Exception("Пустой ответ"))
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        401 -> {
                            Log.d("BudgetRepositoryImpl", "${response.body}")
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        else -> {
                            Log.d("BudgetRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неизвестная ошибка: ${response.body}"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.d("BudgetRepositoryImpl", "${response.error}")
                    Result.failure(Exception("Ошибка сети: ${response.error}"))
                }

                is NetworkResponse.UnknownError -> {
                    Log.d("BudgetRepositoryImpl", "${response.error}")
                    Result.failure(Exception("Неизвестная ошибка: ${response.error}"))
                }
            }
        } catch (e: Exception) {
            Log.d("BudgetRepositoryImpl", "${e.message}")
            Result.failure(Exception("Unknown Error: ${e.message}"))
        }
    }

}