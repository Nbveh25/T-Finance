package ru.practice.t_finance.presentation.screens.goal.goalEdit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CalendarBottomSheet
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.screens.states.ErrorScreen
import ru.practice.t_finance.presentation.screens.states.LoadingScreen
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalEditScreen(
    modifier: Modifier = Modifier,
    viewModel: GoalEditViewModel = hiltViewModel(),
    navController: NavController,
    goalId: Int = -1
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(goalId) {
        viewModel.getGoal(goalId)
    }

    LaunchedEffect(state.value) {
        val currentState = state.value

        if (currentState is GoalEditScreenState.Success && currentState.navigation) {
            navController.navigate(
                route = "${Routes.GOALS_DETAIL_SCREEN}/${goalId}",
            ) {
                launchSingleTop = true
            }
        } else if (currentState is GoalEditScreenState.Success && !currentState.navigation) {
            Unit
        }
    }

    when (val currentState = state.value) {

        is GoalEditScreenState.Initial -> {
            Log.d("GoalEditScreen", "Initial state")
            Unit
        }

        is GoalEditScreenState.Loading -> {
            Log.d("GoalEditScreen", "Loading state")
            LoadingScreen()
        }

        is GoalEditScreenState.Error -> {
            Log.d("GoalEditScreen", "Error state")
            ErrorScreen(
                message = currentState.message,
                onRetry = {
                    viewModel.getGoal(goalId)
                }
            )
        }

        is GoalEditScreenState.Success -> {
            Log.d("GoalEditScreen", "Success state")
            GoalEditContentScreen(
                viewModel = viewModel,
                navController = navController,
                goalId = goalId
            )
        }
    }




}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //GoalEditScreen(onBackClick = {})
        }
    }
}