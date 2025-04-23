package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.Code
import ru.practice.t_finance.domain.model.Name
import ru.practice.t_finance.domain.model.PhoneNumber

interface AuthRepository {
    suspend fun sendCode(phoneNumber: PhoneNumber): Result<Unit>

    suspend fun verifyCode(code: Code): Result<Unit>

    suspend fun saveUserName(name: Name): Result<Unit>
}