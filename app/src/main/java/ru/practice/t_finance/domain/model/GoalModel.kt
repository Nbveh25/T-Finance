package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class GoalModel(
    val goalName: String,
    val currentValue: Int,
    val maxValue: Int
) {
}