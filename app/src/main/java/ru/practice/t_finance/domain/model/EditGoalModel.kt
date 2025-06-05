package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class EditGoalModel(
    val id: Int,
    val name: String,
    val term: String,
    val amount: Double,
    val description: String
)