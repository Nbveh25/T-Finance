package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.Code
import ru.practice.t_finance.domain.model.Name
import ru.practice.t_finance.domain.model.PhoneNumber

interface AuthRepository {
    // Отправка кода подтверждения
    suspend fun sendCode(phoneNumber: PhoneNumber)
    
    // Проверка кода подтверждения
    suspend fun verifyCode(code: Code): Boolean
    
    // Сохранение имени пользователя
    suspend fun saveName(name: Name)
}