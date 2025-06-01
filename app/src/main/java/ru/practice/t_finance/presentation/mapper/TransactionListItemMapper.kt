package ru.practice.t_finance.presentation.mapper

import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.presentation.model.TransactionInput

// presentation/transaction_form/mapper/TransactionInputMapper.kt
fun TransactionInput.toDomain(): TransactionModel? {
    val parsedAmount = value.replace(",", ".").toDoubleOrNull()
    return if (parsedAmount != null) {
        TransactionModel(
            name = name,
            category = category,
            amount = parsedAmount,
            date = date.toString()
        )
    } else {
        null // или выбрасывать exception/Result.failure и т.д.
    }
}
