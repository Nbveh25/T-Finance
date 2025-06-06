package ru.practice.t_finance.data.repository

import android.util.Log
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.response.CategoryResponse
import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    override suspend fun getCategories() : Result<List<CategoryResponse>>{
        return try {
            when (val response = apiService.getCategories()) {
                is NetworkResponse.Success -> {
                    Log.d("CategoryRepositoryImpl", "Success: ${response.data}")
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Log.d("CategoryRepositoryImpl", "Empty success (${response.code})")
                    Result.success(emptyList())
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        401 -> {
                            Log.d("CategoryRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        else -> {
                            Log.d("CategoryRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Ошибка API: ${response.code}"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.e("CategoryRepositoryImpl", "Network Error", response.error)
                    Result.failure(response.error)
                }

                is NetworkResponse.UnknownError -> {
                    Log.e("CategoryRepositoryImpl", "Unknown Error", response.error)
                    Result.failure(response.error)
                }
            }
        } catch (e: Exception) {
            Log.d("CategoryRepositoryImpl", "Unknown Error", e)
            Result.failure(e)
        }
    }

}