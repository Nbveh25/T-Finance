package ru.practice.t_finance.data.repository

import ru.practice.t_finance.domain.model.Code
import ru.practice.t_finance.domain.model.Name
import ru.practice.t_finance.domain.model.PhoneNumber
import ru.practice.t_finance.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    
    // Заглушка для отправки кода (в реальном приложении здесь был бы сетевой запрос)
    override suspend fun sendCode(phoneNumber: PhoneNumber) {
        // Имитация задержки сетевого запроса
        kotlinx.coroutines.delay(1000)
    }
    
    // Заглушка для проверки кода (в реальном приложении проверка через сервер)
    override suspend fun verifyCode(code: Code): Boolean {
        // Имитация задержки сетевого запроса
        kotlinx.coroutines.delay(1000)
        // Для демонстрации принимаем любой код длиной 4 символа
        return code.code.length == 4
    }
    
    // Заглушка для сохранения имени
    override suspend fun saveName(name: Name) {
        // Имитация задержки сетевого запроса
        kotlinx.coroutines.delay(1000)
        // В реальном приложении сохранение в базу данных или отправка на сервер
    }
}