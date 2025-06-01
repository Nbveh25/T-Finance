package ru.practice.t_finance.presentation.model

import org.threeten.bp.LocalDate

data class TransactionInput(
    val name: String = "",
    val category: String = "",
    val value: String = "",
    val date: LocalDate = LocalDate.now()
)