package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.request.TransactionRequest
import ru.practice.t_finance.domain.model.TransactionModel

object TransactionMapper {

    fun toRequest(transactionModel: TransactionModel): TransactionRequest {
        return TransactionRequest(
            amount = transactionModel.,
            categoryId = transactionModel.categoryId,
            date = transactionModel.date,
            description = transactionModel.description
    }

}