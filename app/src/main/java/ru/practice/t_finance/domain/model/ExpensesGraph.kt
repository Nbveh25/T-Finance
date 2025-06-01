package ru.practice.t_finance.domain.model

data class ExpensesGraph(
    val categories: List<Category>,
    val amount: Int
)