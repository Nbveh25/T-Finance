package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.request.SendCodeRequest
import ru.practice.t_finance.data.remote.request.SendNameRequest
import ru.practice.t_finance.data.remote.request.SendSmsRequest
import ru.practice.t_finance.data.remote.response.UserResponse
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.FirstNameModel
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.model.UserModel

object AuthMapper {
    fun toRequest(phoneNumberModel: PhoneNumberModel) = SendSmsRequest(
        phoneNumber = phoneNumberModel.phoneNumber
    )

    fun toRequest(phoneNumberModel: PhoneNumberModel, codeModel: CodeModel) = SendCodeRequest(
        phoneNumber = phoneNumberModel.phoneNumber.toString(),
        code = codeModel.code.toString()
    )

    fun toRequest(firstNameModel: FirstNameModel) = SendNameRequest(
        firstName = firstNameModel.firstName
    )

    fun toModel(userResponse: UserResponse) = UserModel(
        id = userResponse.id,
        phoneNumber = userResponse.phoneNumber,
        firstName = userResponse.firstName,
        budget = userResponse.budget,
        dayOfAdditionOfBudget = userResponse.dayOfAdditionOfBudget
    )

}