package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class AddingTransactionModel(
    val name: String,
    val date: String,
    val categoryId: Int,
    val amount: Double,
    val description: String
)