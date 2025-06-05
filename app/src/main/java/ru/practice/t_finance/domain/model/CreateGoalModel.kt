package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable
import java.io.Serializable

@Immutable
data class CreateGoalModel(
    val name: String,
    val term: String,
    val amount: Double,
    val accumulatedAmount: Double,
    val description: String
) : Serializable