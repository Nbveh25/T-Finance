package ru.practice.t_finance.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.presentation.screens.addingTransaction.AddingTransactionScreen
import ru.practice.t_finance.presentation.screens.authentication.inputName.InputNameScreen
import ru.practice.t_finance.presentation.screens.authentication.auth.AuthScreen
import ru.practice.t_finance.presentation.screens.authentication.consentCode.ConsentCodeScreen
import ru.practice.t_finance.presentation.screens.budgetAllocation.BudgetAllocationScreen
import ru.practice.t_finance.presentation.screens.budgetAllocation.BudgetInputScreen
import ru.practice.t_finance.presentation.screens.expenses.ExpensesScreen
import ru.practice.t_finance.presentation.screens.goal.goalCreate.GoalCreateScreen
import ru.practice.t_finance.presentation.screens.goal.goalDetail.GoalDetailScreen
import ru.practice.t_finance.presentation.screens.goal.goalEdit.GoalEditScreen
import ru.practice.t_finance.presentation.screens.goal.goalList.GoalScreen
import ru.practice.t_finance.presentation.screens.main.MainScreen
import ru.practice.t_finance.presentation.screens.more.MoreScreen
import kotlin.jvm.java


@Composable
fun AppNavigation(
    startDestination: String = Routes.EXPENSES_SCREEN,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        // Authentication
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

        // BottomNavBar
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

        composable(route = Routes.BUDGET_INPUT){
            BudgetInputScreen(
                navController = navController
            )
        }

        composable(
            route = "${Routes.BUDGET_ALLOCATION}?budget={budget}",
            arguments = listOf(
                navArgument("budget")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ){
            BudgetAllocationScreen(
                navController = navController
            )
        }

        composable(route = Routes.ADD_SCREEN) {
            AddingTransactionScreen(
                navController = navController
            )
        }

        // Goals
        composable(route = Routes.GOALS_CREATE_SCREEN) {
            GoalCreateScreen(
                navController = navController,
            )
        }

        composable(
            route = "${Routes.GOALS_EDIT_SCREEN}/{goalId}",
            arguments = listOf(
                navArgument("goalId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getInt("goalId") ?: -1
            GoalEditScreen(
                navController = navController,
                goalId = goalId
            )
        }


        composable(
            route = "${Routes.GOALS_DETAIL_SCREEN}/{goalId}",
            arguments = listOf(
                navArgument("goalId") {
                    type = NavType.IntType  // Используем IntType для ID
                }
            )
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getInt("goalId") ?: -1
            GoalDetailScreen(
                navController = navController,
                goalId = goalId
            )
        }


        composable(route = Routes.EXPENSES_SCREEN){
            ExpensesScreen(
                navController = navController,
            )
        }

    }
} 