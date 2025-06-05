package ru.practice.t_finance.presentation.screens.goal.goalDetail

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.practice.t_finance.presentation.screens.states.ErrorScreen
import ru.practice.t_finance.presentation.screens.states.LoadingScreen
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun GoalDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GoalDetailViewModel = hiltViewModel(),
    goalId: Int
) {


    val state = viewModel.state.collectAsState()

    LaunchedEffect(goalId) {
        viewModel.getGoal(
            goalId = goalId
        )
    }

    when (val currentState = state.value) {

        is GoalDetailScreenState.Initial -> {
            Log.d("GoalDetailScreen", "Initial")
            Unit
        }

        is GoalDetailScreenState.Loading -> {
            Log.d("GoalDetailScreen", "Loading")
            LoadingScreen()
        }

        is GoalDetailScreenState.Error -> {
            Log.d("GoalDetailScreen", "Error ${currentState.message}")
            ErrorScreen(
                message = currentState.message,
                onRetry = {
                    viewModel.getGoal(
                        goalId = goalId
                    )
                }
            )
        }

        is GoalDetailScreenState.Success -> {
            Log.d("GoalDetailScreen", "Success")
            GoalDetailContent(
                goalModel = currentState.goalModel,
                navController = navController
            )
        }

    }
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //GoalDetailScreen(onBackClick = {})
        }
    }
}