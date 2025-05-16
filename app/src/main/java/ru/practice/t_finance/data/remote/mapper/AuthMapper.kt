package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.request.PhoneNumberRequest
import ru.practice.t_finance.domain.model.PhoneNumberModel

object AuthMapper {
    fun toRequest(phoneNumberModel: PhoneNumberModel) = PhoneNumberRequest(
        phoneNumber = phoneNumberModel.phoneNumber
    )
}