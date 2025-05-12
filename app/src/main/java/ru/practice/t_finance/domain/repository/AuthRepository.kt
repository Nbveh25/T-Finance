package ru.practice.t_finance.domain.repository

import retrofit2.Response
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.util.ResponseResult

interface AuthRepository {

    suspend fun sendCode(phoneNumberModel: PhoneNumberModel): ResponseResult<Response<Unit>>

}