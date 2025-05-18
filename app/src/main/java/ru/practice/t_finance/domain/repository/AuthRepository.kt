package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.data.remote.response.SendSmsResponse
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.PhoneNumberModel

interface AuthRepository {

    suspend fun sendSms(phoneNumberModel: PhoneNumberModel): Result<Unit>

    suspend fun sendCode(phoneNumberModel: PhoneNumberModel, codeModel: CodeModel): Result<SendSmsResponse>

}