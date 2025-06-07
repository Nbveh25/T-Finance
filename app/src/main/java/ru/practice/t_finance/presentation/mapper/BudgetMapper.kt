package ru.practice.t_finance.presentation.mapper

import ru.practice.t_finance.domain.model.BudgetModel
import ru.practice.t_finance.presentation.model.BudgetItem

fun BudgetModel.toItem() = BudgetItem(
    amount = amount,
    dayOfAdditionOfBudget = dayOfAdditionOfBudget
)