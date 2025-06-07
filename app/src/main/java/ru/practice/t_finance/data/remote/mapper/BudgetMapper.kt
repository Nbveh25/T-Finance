package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.response.BudgetResponse
import ru.practice.t_finance.domain.model.BudgetModel

object BudgetMapper {
    fun toModel(budgetResponse: BudgetResponse) = BudgetModel(
        amount = budgetResponse.amount,
        dayOfAdditionOfBudget = budgetResponse.dayOfAdditionOfBudget
    )
}