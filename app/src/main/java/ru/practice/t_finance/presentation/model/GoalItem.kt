package ru.practice.t_finance.presentation.model

data class GoalItem(
    val id: Int,
    val name: String,
    val term: String,
    val amount: Double,
    val accumulatedAmount: Double,
    val description: String
)
