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
import androidx.compose.ui.unit.dp
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.presentation.components.GoalSlot
import ru.practice.t_finance.presentation.components.TransactionSlot
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
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
                TransactionModel(
                    iconUrl = "https://avatars.mds.yandex.net/i?id=ce9759b87fb0b2f7276b28e34f0c1ff4e2499d3d-3919804-images-thumbs&n=13",
                    transactionName = "Меган Фокс",
                    categoryName = "Бордель",
                    summa = 55_000_000
                ),
                TransactionModel(
                    iconUrl = "https://avatars.mds.yandex.net/i?id=de31ce5f68663b3c96e2c129b26db7f7057723a0-5243188-images-thumbs&n=13",
                    transactionName = "Марго Робби",
                    categoryName = "Бордель",
                    summa = 55_000_000
                ),
                TransactionModel(
                    iconUrl = "https://avatars.mds.yandex.net/i?id=d3bc9dcac62f4320d08112a3f53d1209b4a17e6b-13061308-images-thumbs&n=13",
                    transactionName = "Ана де Армас",
                    categoryName = "Бордель",
                    summa = 55_000_000
                ),
            )
        )
        GoalSlot(
            goalModelList = listOf(
                GoalModel("Dodge Challenger", currentValue = 1_200_000, maxValue = 7_500_00, description = ""),
                GoalModel("Ford Ferrari", currentValue = 12_000_000, maxValue = 14_900_000, description = ""),
                GoalModel("Lamborghini Countach", currentValue = 12_000_000, maxValue = 77_500_000, description = "")
            )
        )
    }
}

@Preview
@Composable
fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}