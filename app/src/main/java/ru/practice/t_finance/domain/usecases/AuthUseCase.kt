package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(phoneNumberModel: PhoneNumberModel): Result<Unit> {
        return repository.sendCode(phoneNumberModel)
    }

}