package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.request.SendCodeRequest
import ru.practice.t_finance.data.remote.request.SendSmsRequest
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.PhoneNumberModel

object AuthMapper {
    fun toRequest(phoneNumberModel: PhoneNumberModel) = SendSmsRequest(
        phoneNumber = phoneNumberModel.phoneNumber
    )

    fun toRequest(phoneNumberModel: PhoneNumberModel, codeModel: CodeModel) = SendCodeRequest(
        phoneNumber = phoneNumberModel.phoneNumber.toString(),
        code = codeModel.code.toString()
    )
}