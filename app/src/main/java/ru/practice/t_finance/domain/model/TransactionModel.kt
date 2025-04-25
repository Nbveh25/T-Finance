package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class TransactionModel(
    val iconUrl: String,
    val transactionName: String,
    val categoryName: String,
    val summa: Int,
) {
}