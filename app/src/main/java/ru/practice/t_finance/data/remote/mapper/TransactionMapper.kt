package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.request.AddingTransactionRequest
import ru.practice.t_finance.domain.model.AddingTransactionModel

object TransactionMapper {

    fun toRequest(addingTransactionModel: AddingTransactionModel) = AddingTransactionRequest(
        name = addingTransactionModel.name,
        date = addingTransactionModel.date,
        categoryId = addingTransactionModel.categoryId,
        amount = addingTransactionModel.amount,
        description = addingTransactionModel.description
    )


}
