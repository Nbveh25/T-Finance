package ru.practice.t_finance.data.remote.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.practice.t_finance.data.remote.exception.ApiError
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.request.GoalRequest
import ru.practice.t_finance.data.remote.request.SendCodeRequest
import ru.practice.t_finance.data.remote.request.SendNameRequest
import ru.practice.t_finance.data.remote.request.SendSmsRequest
import ru.practice.t_finance.data.remote.request.TransactionRequest
import ru.practice.t_finance.data.remote.response.GoalResponse
import ru.practice.t_finance.data.remote.response.RefreshTokenResponse
import ru.practice.t_finance.data.remote.response.SendSmsResponse

interface ApiService {

    // Authentication
    @POST("api/v1/auth/send-sms")
    suspend fun sendSms(@Body request: SendSmsRequest): NetworkResponse<Unit, ApiError>

    @POST("/api/v1/auth/confirm-sms")
    suspend fun sendCode(@Body request: SendCodeRequest): NetworkResponse<SendSmsResponse, ApiError>

    @POST("/api/v1/user")
    suspend fun sendName(@Body request: SendNameRequest): NetworkResponse<Unit, ApiError>



    // JWT Authorization
    @POST("/api/v1/auth/refresh-token")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): NetworkResponse<RefreshTokenResponse, ApiError>



    // Goals
    @GET("/api/v1/goals")
    suspend fun getGoals(): NetworkResponse<List<GoalResponse>, ApiError>

    @POST("/api/v1/goals")
    suspend fun createGoal(@Body request: GoalRequest): NetworkResponse<Unit, ApiError>

    //@DELETE("/api/v1/goals/{goalId}")
    //suspend fun deleteGoal()

}