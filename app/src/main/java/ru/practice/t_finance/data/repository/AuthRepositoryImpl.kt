package ru.practice.t_finance.data.repository

import android.util.Log
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.mapper.AuthMapper
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.repository.AuthRepository
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.response.SendSmsResponse
import ru.practice.t_finance.domain.model.CodeModel
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun sendSms(phoneNumberModel: PhoneNumberModel): Result<Unit> {
        return try {
            when (val response = apiService.sendSms(AuthMapper.toRequest(phoneNumberModel))) {

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
                            Log.d("AuthRepositoryImpl", "Invalid phone number: ${response.code} - ${response.body?.message}")
                            Result.failure(Exception("Неправильный формат номера телефона"))
                        }
                        else -> {
                            Log.d("AuthRepositoryImpl", "API Error: ${response.code} - ${response.body?.message}")
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
        } catch (e: Exception) {
            Log.d("AuthRepositoryImpl", "Exception: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun sendCode(
        phoneNumberModel: PhoneNumberModel,
        codeModel: CodeModel
    ): Result<SendSmsResponse> {
        return try {
            when(val response = apiService.sendCode(AuthMapper.toRequest(phoneNumberModel, codeModel))) {
                is NetworkResponse.Success -> {
                    val tokens = response.data
                    Log.d("AuthRepositoryImpl", "Response: $tokens")
                    Result.success(
                        SendSmsResponse(
                            accessToken = tokens.accessToken,
                            refreshToken = tokens.refreshToken,
                            tokenType = tokens.tokenType,
                            expiresIn = tokens.expiresIn
                        )
                    )
                }
                is NetworkResponse.ApiError -> {
                    when (response.code) {
                        400 -> Result.failure(Exception("Неправильный код"))
                        404 -> Result.failure(Exception("Номер телефона не найден"))
                        else -> Result.failure(Exception("Unknown error: ${response.code}"))
                    }
                }
                is NetworkResponse.NetworkError -> {
                    Result.failure(response.error)
                }
                is NetworkResponse.UnknownError -> {
                    Result.failure(response.error)
                }
                is NetworkResponse.EmptySuccess -> {
                    Result.failure(Exception("Empty success"))
                }
            }
        } catch (e: Exception) {
            Log.d("AuthRepositoryImpl", "Exception: ${e.message}")
            Result.failure(e)
        }
    }
}