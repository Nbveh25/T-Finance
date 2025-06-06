package ru.practice.t_finance.domain.usecases.expenses

import ru.practice.t_finance.domain.model.ExpensesGraph
import ru.practice.t_finance.domain.repository.ExpensesRepository
import javax.inject.Inject

class TransactionsByCategoryUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend fun invoke(startDate: String, endDate: String): Result<ExpensesGraph> {
        val data = repository.getExpenses(startDate, endDate)
        return data
    }
}