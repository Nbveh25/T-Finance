package ru.practice.t_finance.domain.usecases.auth

import jakarta.inject.Inject
import ru.practice.t_finance.domain.model.UserModel
import ru.practice.t_finance.domain.repository.AuthRepository

class GetUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<UserModel> {
        return repository.getUser()
    }
}