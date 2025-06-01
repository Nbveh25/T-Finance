package ru.practice.t_finance.data.repository

import android.util.Log
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.domain.model.ExpensesGraph
import ru.practice.t_finance.domain.repository.ExpensesRepository
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ExpensesRepository {


    //    override suspend fun getExpenses(
//        startDate: String,
//        endDate: String
//    ): Result<ExpensesGraph> {
//        return try {
//            when (val response = api.getExpenses(startDate,endDate) ){
//                is NetworkResponse.Success -> {Result.success(response.data.categories)}
//                is NetworkResponse.EmptySuccess -> {Result.success(Unit)}
//                is NetworkResponse.ApiError -> {
//                    400 -> {
//                        Log.d("TransactionRepositoryImpl", "${response.code}")
//                        Result.failure(Exception("Incorrect Payload"))
//                    }
//                    401 -> {
//                        Log.d("TransactionRepositoryImpl", "${response.code}")
//                        Result.failure(Exception("Неавторизованный пользователь"))
//                    }
//                    404 -> {
//                        Log.d("TransactionRepositoryImpl", "${response.code}")
//                        Result.failure(Exception("Неправильный формат даты"))
//                    }
//                    else -> {
//                        Log.d("TransactionRepositoryImpl", "${response.code}")
//                        Result.failure(Exception("Неизвестная ошибка"))
//                    }
//                }
//
//
//                is NetworkResponse.NetworkError -> {
//                    Log.d("AuthRepositoryImpl", "Network Error: ${response.error.message}")
//                    Result.failure(response.error)
//                }
//
//                is NetworkResponse.UnknownError -> {
//                    Log.d("AuthRepositoryImpl", "Unknown Error: ${response.error.message}")
//                    Result.failure(response.error)
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("TransactionRepositoryImpl", "Network Error ${e.message}")
//            return Result.failure(e)
//        } as Result<ExpensesGraph>
//    }
    override suspend fun getExpenses(
        startDate: String,
        endDate: String
    ): Result<ExpensesGraph> {
        TODO("Not yet implemented")
    }

}