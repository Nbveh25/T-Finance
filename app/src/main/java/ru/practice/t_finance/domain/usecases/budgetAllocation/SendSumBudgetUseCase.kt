package ru.practice.t_finance.domain.usecases.budgetAllocation

import ru.practice.t_finance.domain.repository.InitialBudgetRepository
import javax.inject.Inject

class SendSumBudgetUseCase @Inject constructor(
    private val repository: InitialBudgetRepository
) {
    suspend fun invoke(amount : String, day: String) : Result<Unit> {
        val data = repository.sendInitialBudget(amount,day)
        return data
    }
}