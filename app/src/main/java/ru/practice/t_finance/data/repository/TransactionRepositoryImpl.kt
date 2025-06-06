package ru.practice.t_finance.data.repository

import android.util.Log
import android.util.Log.e
import jakarta.inject.Inject
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.mapper.TransactionMapper
import ru.practice.t_finance.data.remote.response.InnerTransactionsResponse
import ru.practice.t_finance.data.remote.response.TransactionsResponse
import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.domain.repository.TransactionRepository

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun addTransaction(addingTransactionModel: AddingTransactionModel): Result<Unit> {
        return try {
            when (val response = apiService.addTransaction(TransactionMapper.toRequest(addingTransactionModel))) {

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

    override suspend fun getTransactionsListByDate(
        startDate: String,
        endDate: String
    ): Result<List<TransactionModel>> {
        return try {
            when (val response = apiService.getTransactionsByDate(startDate,endDate)){
                is NetworkResponse.Success -> {
                    Result.success(response.data.transactions.map{
                        it.toModel()
                    })
                }

                is NetworkResponse.EmptySuccess -> {
                    Result.success(emptyList())
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        400 -> {
                            Result.failure(Exception("Incorrect parameters value (blank name, negative categoryId or pages, incorrect date format or other format problem)"))
                        }

                        401 -> {
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }


                        else -> {
                            Result.failure(Exception("Неизвестнаzj ошибка  ${response}"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Result.failure(Exception("Ошибка сети"))
                }

                is NetworkResponse.UnknownError -> {
                    Result.failure(Exception("Неизвестная ошибкаk ${response.error}"))
                }
            }
        } catch (e: Exception){
            return Result.failure(e)
        }
    }

}

fun InnerTransactionsResponse.toModel(): TransactionModel {
    return TransactionModel(
        name = name,
        category = category.name,
        amount = amount,
        date = date,
        imageUrl = category.icon
    )
}