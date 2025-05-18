package ru.practice.t_finance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.practice.t_finance.presentation.screens.addingTransaction.AddingTransactionScreen
import ru.practice.t_finance.presentation.screens.authentication.InputNameScreen
import ru.practice.t_finance.presentation.screens.authentication.auth.AuthScreen
import ru.practice.t_finance.presentation.screens.authentication.consentCode.ConsentCodeScreen
import ru.practice.t_finance.presentation.screens.budgetAllocation.BudgetInputScreen
import ru.practice.t_finance.presentation.screens.goalList.GoalScreen
import ru.practice.t_finance.presentation.screens.main.MainScreen
import ru.practice.t_finance.presentation.screens.more.MoreScreen

@Composable
fun AppNavigation(
    startDestination: String = Routes.MAIN_SCREEN,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = Routes.AUTH_SCREEN) {
            AuthScreen(
                navController = navController
            )
        }

        composable(
            route = "${Routes.CONSENT_CODE_SCREEN}?phoneNumber={phoneNumber}",
            arguments = listOf(
                navArgument("phoneNumber")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            ConsentCodeScreen(
                modifier = Modifier,
                navController = navController
            )
        }
        
        composable(
            route = Routes.INPUT_NAME_SCREEN
        ) { 
            InputNameScreen(
                navController = navController,
            )
        }
        

        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(
                navController = navController
            )
        }

        composable(route = Routes.BUDGET_SCREEN) {
            BudgetInputScreen(
                navController = navController,
            )
        }

        composable(route = Routes.GOALS_SCREEN) {
            GoalScreen(
                navController = navController
            )
        }

        composable(route = Routes.MORE_SCREEN) {
            MoreScreen(
                navController = navController
            )
        }

        composable(route = Routes.ADD_SCREEN) {
            AddingTransactionScreen(
                navController = navController
            )
        }

    }
} 