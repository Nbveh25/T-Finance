package ru.practice.t_finance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.practice.t_finance.presentation.screens.addingTransaction.AddingTransactionScreen
import ru.practice.t_finance.presentation.screens.budgetAllocation.BudgetInputScreen
import ru.practice.t_finance.presentation.screens.goalList.GoalScreen
import ru.practice.t_finance.presentation.screens.main.MainScreen
import ru.practice.t_finance.presentation.screens.more.MoreScreen

@Composable
fun AppNavigation(
    startDestination: String = Routes.MAIN_SCREEN,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(
                navController = navController
            )
        }

        composable(Routes.BUDGET_SCREEN) {
            BudgetInputScreen(
                navController = navController
            )
        }

        composable(Routes.GOALS_SCREEN) {
            GoalScreen(
                navController = navController
            )
        }

        composable(Routes.MORE_SCREEN) {
            MoreScreen(
                navController = navController
            )
        }

        composable(Routes.ADD_SCREEN) {
            AddingTransactionScreen(
                navController = navController
            )
        }
    }
} 