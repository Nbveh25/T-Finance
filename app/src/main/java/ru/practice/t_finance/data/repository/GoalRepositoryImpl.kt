package ru.practice.t_finance.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.mapper.GoalMapper
import ru.practice.t_finance.data.remote.response.GoalResponse
import ru.practice.t_finance.domain.model.GetGoalModel
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.domain.model.EditGoalModel
import ru.practice.t_finance.domain.repository.GoalRepository
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GoalRepository {

    override suspend fun getGoals(): Result<List<GoalResponse>> {
        return try {
            when (val response = apiService.getGoals()) {
                is NetworkResponse.Success -> {
                    Log.d("GoalRepositoryImpl", "Success: ${response.data}")
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Log.d("GoalRepositoryImpl", "Empty success (${response.code})")
                    Result.success(emptyList())
                }

                is NetworkResponse.ApiError -> {
                    Log.d("GoalRepositoryImpl", "API Error ${response.code}")
                    when (response.code) {
                        401 -> Result.failure(Exception("Неавторизованный пользователь (${response.code})"))
                        else -> Result.failure(Exception("Ошибка API: ${response.code}"))
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.e("GoalRepositoryImpl", "Network Error", response.error)
                    Result.failure(response.error)
                }

                is NetworkResponse.UnknownError -> {
                    Log.e("GoalRepositoryImpl", "Unknown Error", response.error)
                    Result.failure(response.error)
                }
            }
        } catch (e: Exception) {
            Log.d("GoalRepositoryImpl", "Unknown Error", e)
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createGoal(createGoalModel: CreateGoalModel): Result<Unit> {
        return try {
            when (val response = apiService.createGoal(GoalMapper.toRequest(createGoalModel))) {

                is NetworkResponse.Success -> {
                    Log.d("GoalRepositoryImpl", "Success: ${response.data}")
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Log.d("GoalRepositoryImpl", "EmptySuccess: ${response.code}")
                    Result.success(Unit)
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        400 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Incorrect Payload"))
                        }
                        401 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        else ->  {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неизвестная ошибка"))
                        }
                    }

                }

                is NetworkResponse.NetworkError -> {
                    Log.d("GoalRepositoryImpl", "Network Error ${response.error}")
                    Result.failure(Exception("Ошибка сети"))
                }

                is NetworkResponse.UnknownError -> {
                    Log.d("GoalRepositoryImpl", "Unknown Error ${response.error}")
                    Result.failure(Exception("Неизвестная ошибка"))
                }

            }
        } catch (e: Exception) {
            Log.d("GoalRepositoryImpl", "Network Error ${e.message}")
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun editGoal(goalModel: EditGoalModel): Result<Unit> {
        return try {
            when(val response = apiService.editGoal(GoalMapper.toRequest(goalModel))) {
                is NetworkResponse.Success -> {
                    Log.d("GoalRepositoryImpl", "Success: ${response.data}")
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Log.d("GoalRepositoryImpl", "EmptySuccess: ${response.code}")
                    Result.success(Unit)
                }

                is NetworkResponse.ApiError<*> -> {
                    when(response.code) {
                        400 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Incorrect Payload"))
                        }
                        401 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        403 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Доступ запрещен"))
                        }
                        404 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Ресурс не найден"))
                        }
                        else ->  {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неизвестная ошибка"))
                        }
                    }
                }
                is NetworkResponse.NetworkError -> {
                    Log.d("GoalRepositoryImpl", "Network Error ${response.error}")
                    Result.failure(Exception("Ошибка сети"))
                }
                is NetworkResponse.UnknownError -> {
                    Log.d("GoalRepositoryImpl", "Unknown Error ${response.error}")
                    Result.failure(Exception("Неизвестная ошибка"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGoalById(id: Int): Result<GoalResponse> {
        return try {
            when (val response = apiService.getGoalById(id)) {
                is NetworkResponse.Success -> {
                    Result.success(response.data)
                }

                is NetworkResponse.EmptySuccess -> {
                    Result.failure(Exception("Пустой ответ от сервера"))
                }

                is NetworkResponse.ApiError -> {
                    when(response.code) {
                        401 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неавторизованный пользователь"))
                        }
                        403 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Доступ запрещен"))
                        }
                        404 -> {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Ресурс не найден"))
                        }
                        else ->  {
                            Log.d("GoalRepositoryImpl", "${response.code}")
                            Result.failure(Exception("Неизвестная ошибка"))
                        }
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.d("GoalRepositoryImpl", "Network Error ${response.error}")
                    Result.failure(Exception("Ошибка сети"))
                }
                is NetworkResponse.UnknownError -> {
                    Log.d("GoalRepositoryImpl", "Unknown Error ${response.error}")
                    Result.failure(Exception("Неизвестная ошибка"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}