package ru.practice.t_finance.domain.validator

import jakarta.inject.Inject

class GoalEditValidator @Inject constructor() {

    fun amountValid(amount: String): Boolean {
        val number = amount.trim().toDoubleOrNull()

        return number != null && number > 0
    }


}