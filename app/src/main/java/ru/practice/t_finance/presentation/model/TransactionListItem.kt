package ru.practice.t_finance.presentation.model

data class TransactionListItem(
    val name: String,
    val category: String,
    val imageUrl: String?,
    val amountFormatted: String
)