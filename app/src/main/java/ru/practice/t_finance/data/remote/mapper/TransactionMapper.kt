package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.request.TransactionRequest
import ru.practice.t_finance.domain.model.TransactionModel

object TransactionMapper {

    fun toRequest(transactionModel: TransactionModel): TransactionRequest {

        val categoryId = when (transactionModel.category){
            "Products" -> 3
            else -> {1}
        }

        return TransactionRequest(
            amount = transactionModel.amount,
            categoryId = categoryId,
            date = transactionModel.date,
            description = "" )
    }

}