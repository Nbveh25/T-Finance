package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.AddingTransactionModel
import ru.practice.t_finance.domain.model.TransactionModel

interface TransactionRepository {

    suspend fun getTransactionsListByDate(startDate: String, endDate: String): Result<List<TransactionModel>>

    suspend fun addTransaction(addingTransactionModel: AddingTransactionModel): Result<Unit>

}