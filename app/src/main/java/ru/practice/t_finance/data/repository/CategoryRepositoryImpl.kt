package ru.practice.t_finance.data.repository

import androidx.compose.ui.graphics.Color
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.domain.model.Category
import android.util.Log
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.request.CategoryRequest
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
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Result.success(emptyList())
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        401 -> {
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        else -> {
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

    override suspend fun sendCategories(data: List<Category>): Result<Unit> {
        val newData = data.map {
            CategoryRequest(
                categoryId = it.id.toString(),
                percent = it.value.toString(),
            )
        }
        return try {
            when (val response = apiService.sendCategories(newData)) {
                is NetworkResponse.Success -> {
                    Log.d("AuthRepositoryImpl", "Response: ${response.data}")
                    Result.success(response.data)
                }

                // 204
                is NetworkResponse.EmptySuccess -> {
                    Log.d("AuthRepositoryImpl", "Empty success: ${response.code}")
                    Result.success(Unit)
                }

                is NetworkResponse.ApiError -> {
                    when (response.code) {
                        400 -> {
                            Result.failure(Exception("Incorrect payload (sum of percent not equal 100 or values of fields 'percent' and 'notificationLimit' do not belong to the interval 0-100 or categories are repeated)"))
                        }

                        else -> {
                            Log.d(
                                "AuthRepositoryImpl",
                                "API Error: ${response.code} - ${response.body?.message}"
                            )
                            Result.failure(Exception("Unknown API error"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.d("AuthRepositoryImpl", "Network Error: ${response.error.message}")
                    Result.failure(response.error)
                }

                is NetworkResponse.UnknownError -> {
                    Log.d("AuthRepositoryImpl", "Unknown Error: ${response.error.message}")
                    Result.failure(response.error)
                }

            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }


}