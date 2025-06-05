package ru.practice.t_finance.presentation.screens.goal.goalDetail

import ru.practice.t_finance.domain.model.GetGoalModel

sealed class GoalDetailScreenState {
    object Initial: GoalDetailScreenState()
    object Loading: GoalDetailScreenState()
    data class Success(val goalModel: GetGoalModel): GoalDetailScreenState()
    data class Error(val message: String): GoalDetailScreenState()
}