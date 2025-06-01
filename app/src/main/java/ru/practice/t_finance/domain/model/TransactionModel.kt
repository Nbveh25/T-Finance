package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable
import org.threeten.bp.LocalDate

@Immutable
data class TransactionModel(
    val name: String,
    val category: String,
    val amount: Double,
    val date: String,
    val imageUrl: String? = null
)