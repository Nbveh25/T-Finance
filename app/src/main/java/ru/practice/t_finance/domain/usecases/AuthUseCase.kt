package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.model.Code
import ru.practice.t_finance.domain.model.Name
import ru.practice.t_finance.domain.model.PhoneNumber
import ru.practice.t_finance.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    // Отправка кода подтверждения на номер телефона
    suspend fun sendCode(phoneNumber: PhoneNumber) {
        repository.sendCode(phoneNumber)
    }

    // Повторная отправка кода
    suspend fun resendCode(phoneNumber: PhoneNumber) {
        repository.sendCode(phoneNumber)
    }

    // Проверка кода подтверждения
    suspend fun verifyCode(code: Code): Boolean {
        return repository.verifyCode(code)
    }

    // Сохранение имени пользователя
    suspend fun saveName(name: Name) {
        repository.saveName(name)
    }
}