package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserModel(
    val id: Int,
    val phoneNumber: String,
    val firstName: String,
    val budget: Double,
    val dayOfAdditionOfBudget: Int
)
