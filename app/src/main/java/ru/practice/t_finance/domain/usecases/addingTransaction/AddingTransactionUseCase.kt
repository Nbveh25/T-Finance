package ru.practice.t_finance.domain.usecases.addingTransaction

import android.util.Log
import jakarta.inject.Inject
import ru.practice.t_finance.domain.model.AddingTransactionModel
import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.domain.repository.TransactionRepository

class AddingTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(addingTransactionModel: AddingTransactionModel): Result<Unit> {
        Log.d("AddTransactionUseCase", "Transaction: ${addingTransactionModel.amount}")
        return repository.addTransaction(addingTransactionModel)
    }
}