package ru.practice.t_finance.data.repository

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

                    Result.success(BudgetMapper.toModel(response.data))
                }

                is NetworkResponse.EmptySuccess -> {

                    Result.failure(Exception("Пустой ответ"))
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        401 -> {

                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        else -> {

                            Result.failure(Exception("Неизвестная ошибка: ${response.body}"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {

                    Result.failure(Exception("Ошибка сети: ${response.error}"))
                }

                is NetworkResponse.UnknownError -> {

                    Result.failure(Exception("Неизвестная ошибка: ${response.error}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown Error: ${e.message}"))
        }
    }

}