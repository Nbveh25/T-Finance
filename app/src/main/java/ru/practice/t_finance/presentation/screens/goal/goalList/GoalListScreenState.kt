package ru.practice.t_finance.presentation.screens.goal.goalList

sealed class GoalListScreenState() {
    object Initial : GoalListScreenState()
    object Loading : GoalListScreenState()
    class Success() : GoalListScreenState()
    data class Error(val message: String) : GoalListScreenState()
}