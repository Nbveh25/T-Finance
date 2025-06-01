package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.repository.ExpensesRepository
import javax.inject.Inject

class ExpenseSumUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend fun invoke(startDate: String, endDate: String) : Int {
        return 2345
    }
}