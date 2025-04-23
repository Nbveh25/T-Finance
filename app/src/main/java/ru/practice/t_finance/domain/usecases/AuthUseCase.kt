package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.model.Code
import ru.practice.t_finance.domain.model.Name
import ru.practice.t_finance.domain.model.PhoneNumber
import ru.practice.t_finance.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun sendCode(phoneNumber: PhoneNumber): Result<Unit> {
        return repository.sendCode(phoneNumber)
    }

    suspend fun verifyCode(code: Code): Result<Unit> {
        return repository.verifyCode(code)
    }

    suspend fun saveUserName(name: Name): Result<Unit> {
        return repository.saveUserName(name)
    }
}