package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.data.remote.response.SendSmsResponse
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.FirstNameModel
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.model.UserModel

interface AuthRepository {

    suspend fun sendSms(phoneNumberModel: PhoneNumberModel): Result<Unit>

    suspend fun sendCode(phoneNumberModel: PhoneNumberModel, codeModel: CodeModel): Result<SendSmsResponse>

    suspend fun sendName(firstNameModel: FirstNameModel): Result<Unit>

    suspend fun getUser(): Result<UserModel>


}