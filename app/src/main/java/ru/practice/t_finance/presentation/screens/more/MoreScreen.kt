package ru.practice.t_finance.presentation.screens.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CashbackBonusCard
import ru.practice.t_finance.presentation.components.MoreCard
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.echo),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
        )

        CashbackBonusCard()

        MoreCard(name = "Совместный бюджет", desc = "Поделись с женой")

        MoreCard(name = "Настройки", desc = "Настрой свою жизнь")

    }
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //MoreScreen()
        }
    }
}
