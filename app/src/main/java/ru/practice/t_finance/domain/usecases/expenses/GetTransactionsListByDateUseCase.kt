package ru.practice.t_finance.domain.usecases.expenses

import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.domain.repository.ExpensesRepository
import ru.practice.t_finance.domain.repository.TransactionRepository
import ru.practice.t_finance.presentation.model.TransactionListItem
import javax.inject.Inject
import kotlin.Result

class GetTransactionsListByDateUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend fun invoke(startDate: String, endDate: String) : Result<List<TransactionModel>>{
        return repository.getTransactionsListByDate(startDate,endDate)
    }
}