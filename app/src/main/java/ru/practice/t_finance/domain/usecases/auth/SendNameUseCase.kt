package ru.practice.t_finance.domain.usecases.auth

import android.util.Log
import jakarta.inject.Inject
import ru.practice.t_finance.domain.model.FirstNameModel
import ru.practice.t_finance.domain.repository.AuthRepository

class SendNameUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(firstNameModel: FirstNameModel): Result<Unit> {
        Log.d("AuthUseCase", "First name: ${firstNameModel.firstName}")
        return repository.sendName(firstNameModel = firstNameModel)
    }

}