package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.TransactionModel

interface TransactionRepository {
    suspend fun addTransaction(transactionModel: TransactionModel): Result<Unit>

    suspend fun getTransactionsListByDate(startDate: String, endDate: String): Result<List<TransactionModel>>
}