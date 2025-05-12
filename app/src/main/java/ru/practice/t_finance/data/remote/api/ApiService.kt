package ru.practice.t_finance.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.practice.t_finance.data.remote.request.PhoneNumberRequest

interface ApiService {

    // Authentication
    @POST("api/v1/auth/send-sms")
    suspend fun sendCode(@Body request: PhoneNumberRequest) : Response<Unit>

}