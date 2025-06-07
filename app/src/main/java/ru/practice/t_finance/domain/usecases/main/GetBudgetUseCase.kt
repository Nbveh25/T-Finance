package ru.practice.t_finance.domain.usecases.main

import jakarta.inject.Inject
import ru.practice.t_finance.domain.model.BudgetModel
import ru.practice.t_finance.domain.repository.BudgetRepository

class GetBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {

    suspend operator fun invoke(): Result<BudgetModel> {
        return repository.getBudget()
    }

}