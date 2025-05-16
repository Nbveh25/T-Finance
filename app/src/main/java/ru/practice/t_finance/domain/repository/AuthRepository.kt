package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.PhoneNumberModel

interface AuthRepository {

    suspend fun sendCode(phoneNumberModel: PhoneNumberModel): Result<Unit>

}