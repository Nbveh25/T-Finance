package ru.practice.t_finance.presentation.screens.goal.goalList

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import ru.practice.t_finance.presentation.components.GoalsList
import ru.practice.t_finance.presentation.navigation.Routes

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GoalViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            when (val currentState = state.value) {
                is GoalListScreenState.Initial -> {
                    Unit
                }
                is GoalListScreenState.Loading -> {
                    LoadingScreen()
                }
                is GoalListScreenState.Error -> {
                    ErrorScreen(currentState.message)
                }
                is GoalListScreenState.Success -> {
                    ContentScreen(goals = viewModel.goalList, navController = navController)
                }

            }


        }
        CustomButton(
            text = stringResource(R.string.add_goal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.horizontal_screen_padding),
                    vertical = dimensionResource(R.dimen.padding_extra_small)
                ),
            onClick = {
                navController.navigate(Routes.GOALS_EDIT_SCREEN)
            }
        )
    }


}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //GoalScreen()
        }
    }
}