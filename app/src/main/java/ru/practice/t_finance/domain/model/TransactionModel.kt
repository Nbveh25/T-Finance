package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class TransactionModel(
    val amount: Double,
    val categoryId: Int,
    val date: String,
    val description: String
) {
}