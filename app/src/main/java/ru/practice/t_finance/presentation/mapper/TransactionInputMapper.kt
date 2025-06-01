package ru.practice.t_finance.presentation.mapper


import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.presentation.model.TransactionListItem

fun TransactionModel.toListItem(): TransactionListItem {
    return TransactionListItem(
        name = name,
        category = category,
        imageUrl = imageUrl,
        amountFormatted = amount.toString() // пример форматирования
    )
}