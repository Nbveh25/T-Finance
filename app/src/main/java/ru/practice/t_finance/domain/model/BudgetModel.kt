package ru.practice.t_finance.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class BudgetModel(
    val amount: Double,
    val dayOfAdditionOfBudget: Int
)