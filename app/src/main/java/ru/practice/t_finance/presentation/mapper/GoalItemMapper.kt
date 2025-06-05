package ru.practice.t_finance.presentation.mapper

import ru.practice.t_finance.domain.model.GetGoalModel
import ru.practice.t_finance.presentation.model.GoalItem

fun GetGoalModel.toItem(): GoalItem {
    return GoalItem(
        id = id,
        name = name,
        term = term,
        amount = amount,
        accumulatedAmount = accumulatedAmount,
        description = description,
    )
}