package ru.practice.t_finance.presentation.screens.goal.goalList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.presentation.components.GoalsList

@Composable
fun ContentScreen(goals: List<GoalModel>, navController: NavController) {
    GoalsList(
        modifier = Modifier.fillMaxSize(),
        goals = goals,
        navController = navController
    )
}