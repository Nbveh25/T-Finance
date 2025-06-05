package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class GetGoalModel(
    val id: Int,
    val name: String,
    val term: String,
    val amount: Double,
    val accumulatedAmount: Double,
    val description: String
) {
}