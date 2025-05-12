package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun sendCode(phoneNumberModel: PhoneNumberModel) = repository.sendCode(phoneNumberModel)

}