package ru.practice.t_finance.data.remote.mapper

import ru.practice.t_finance.data.remote.response.BudgetBalanceResponse
import ru.practice.t_finance.domain.model.BudgetModel

object BudgetMapper {
    fun toModel(budgetBalanceResponse: BudgetBalanceResponse) = BudgetModel(
        balance = budgetBalanceResponse.balance
    )
}