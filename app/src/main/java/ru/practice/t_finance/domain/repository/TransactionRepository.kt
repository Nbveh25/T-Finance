package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.AddingTransactionModel

interface TransactionRepository {
    suspend fun addTransaction(transactionModel: TransactionModel): Result<Unit>

    suspend fun getTransactionsListByDate(startDate: String, endDate: String): Result<List<TransactionModel>>

    suspend fun addTransaction(addingTransactionModel: AddingTransactionModel): Result<Unit>

}