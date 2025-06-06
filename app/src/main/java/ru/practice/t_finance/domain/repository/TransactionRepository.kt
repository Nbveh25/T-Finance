package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.AddingTransactionModel

interface TransactionRepository {
    suspend fun addTransaction(addingTransactionModel: AddingTransactionModel): Result<Unit>
}