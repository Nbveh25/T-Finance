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
import androidx.compose.ui.Modifier
import ru.practice.t_finance.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.presentation.components.GoalSlot
import ru.practice.t_finance.presentation.components.TransactionSlot
import ru.practice.t_finance.presentation.model.TransactionListItem
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    //viewModel: MainViewModel = hiltViewModel(),
) {
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
            Column {
                Text(
                    text = "Доступно",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "3 556 Р",
                    style = MaterialTheme.typography.bodyLarge
                )
                // Круговая диграмма
            }
        }
        //.....................

        TransactionSlot(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            transactionModelList = listOf(
                TransactionListItem(
                    imageUrl = "https://avatars.mds.yandex.net/i?id=ce9759b87fb0b2f7276b28e34f0c1ff4e2499d3d-3919804-images-thumbs&n=13",
                    name = "Меган Фокс",
                    category = "Бордель",
                    amountFormatted = "5000"
                ),

            )
        )
        GoalSlot(
            createGoalModelList = listOf(
                CreateGoalModel(
                    "Dodge Challenger",
                    accumulatedAmount = 1_200_000.0,
                    amount = 7_500_00.0,
                    description = "",
                    term = ""
                ),
                CreateGoalModel(
                    "Ford Ferrari",
                    accumulatedAmount = 12_000_000.0,
                    amount = 14_900_000.0,
                    description = "",
                    term = ""
                ),
                CreateGoalModel(
                    "Lamborghini Countach",
                    accumulatedAmount = 12_000_000.0,
                    amount = 77_500_000.0,
                    description = "",
                    term = ""
                )
            )
        )
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