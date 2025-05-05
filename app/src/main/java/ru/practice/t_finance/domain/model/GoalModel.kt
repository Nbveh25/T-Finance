package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class GoalModel(
    val name: String,
    val description: String,
    val currentValue: Int,
    val maxValue: Int
)