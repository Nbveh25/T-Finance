package ru.practice.t_finance.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import ru.practice.t_finance.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.practice.t_finance.presentation.components.GoalSlot
import ru.practice.t_finance.presentation.components.MainScreenSlotShimmer
import ru.practice.t_finance.presentation.components.TransactionSlot
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainVewModel = hiltViewModel(),
) {
    val budgetState = viewModel.budgetState.collectAsState()
    val transactionsState = viewModel.transactionsState.collectAsState()
    val goalsState = viewModel.goalsState.collectAsState()

    Column(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_extra_small),
                vertical = dimensionResource(R.dimen.padding_medium)
            )
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            )
        ) {
            when(budgetState.value) {
                is BudgetUIState.Initial -> {
                    Unit
                }
                is BudgetUIState.Loading -> {
                    Unit
                }
                is BudgetUIState.Error -> {
                    Unit
                }
                is BudgetUIState.Success -> {
                    Column {
                        Text(
                            text = "Доступно",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${(budgetState.value as BudgetUIState.Success).data.balance}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        // Круговая диграмма
                    }
                }
            }

        }
        //.....................

        when (transactionsState.value) {
            is TransactionUIState.Initial -> {
                Unit
            }

            is TransactionUIState.Loading -> {
                MainScreenSlotShimmer()
            }

            is TransactionUIState.Error -> {
                MainScreenSlotShimmer()
            }

            is TransactionUIState.Success -> {
                TransactionSlot(
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    transactionModelList = (transactionsState.value as TransactionUIState.Success).data,
                    onClick = { navController.navigate(Routes.EXPENSES_SCREEN) }
                )
            }
        }

        when (goalsState.value) {
            is GoalUIState.Initial -> {
                Unit
            }

            is GoalUIState.Loading -> {
                MainScreenSlotShimmer()
            }

            is GoalUIState.Error -> {
                MainScreenSlotShimmer()
            }

            is GoalUIState.Success -> {
                GoalSlot(
                    goalItemList = (goalsState.value as GoalUIState.Success).data,
                    onClick = { navController.navigate(Routes.GOALS_SCREEN) }
                )
            }
        }


    }
}

@Preview
@Composable
fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen(navController = rememberNavController())
        }
    }
}