package ru.practice.t_finance.data.repository

import android.util.Log
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.mapper.AuthMapper
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.repository.AuthRepository
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.response.SendSmsResponse
import ru.practice.t_finance.data.remote.token.TokenService
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.FirstNameModel
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenService: TokenService
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
                            Log.d(
                                "AuthRepositoryImpl",
                                "Invalid phone number: ${response.code} - ${response.body?.message}"
                            )
                            Result.failure(Exception("Неправильный формат номера телефона"))
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
            when (val response =
                apiService.sendCode(AuthMapper.toRequest(phoneNumberModel, codeModel))) {
                is NetworkResponse.Success -> {
                    val tokens = response.data
                    Log.d("AuthRepositoryImpl", "Response: $tokens")
                    Log.d("AuthRepositoryImpl", "Access token: ${tokens.accessToken}")
                    Log.d("AuthRepositoryImpl", "Refresh token: ${tokens.refreshToken}")
                    tokenService.setTokens(
                        access = tokens.accessToken,
                        refresh = tokens.refreshToken
                    )
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

    override suspend fun sendName(firstNameModel: FirstNameModel): Result<Unit> {
        return try {
            when (val response =
                apiService.sendName(AuthMapper.toRequest(firstNameModel))) {

                is NetworkResponse.EmptySuccess -> {
                    Log.d("AuthRepositoryImpl", "Empty success: ${response.code}")
                    Result.success(Unit)
                }

                is NetworkResponse.Success -> {
                    Log.d("AuthRepositoryImpl", "Success: ${response.data}")
                    Result.success(response.data)
                }

                is NetworkResponse.ApiError -> {
                    when (response.code) {
                        400 -> Result.failure(Exception("Неправильный формат имени"))
                        401 -> Result.failure(Exception("Пользователь неавторизован"))
                        else -> Result.failure(Exception("Unknown error: ${response.code}"))
                    }
                }

                is NetworkResponse.NetworkError -> {
                    Log.d("AuthRepositoryImpl", "NetworkError: ${response.error}")
                    Result.failure(response.error)
                }

                is NetworkResponse.UnknownError -> {
                    Log.d("AuthRepositoryImpl", "UnknownError: ${response.error}")
                    Result.failure(response.error)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}