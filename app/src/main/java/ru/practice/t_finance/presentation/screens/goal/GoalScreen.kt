package ru.practice.t_finance.presentation.screens.goal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomSpinner
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.components.DatePicker
import ru.practice.t_finance.presentation.components.Goal
import ru.practice.t_finance.presentation.components.GoalCard
import ru.practice.t_finance.presentation.components.GoalsList
import ru.practice.t_finance.presentation.screens.addingTransaction.AddingTransaction
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GoalScreen(
    modifier: Modifier = Modifier,
) {
    val goals = listOf(
        GoalModel(
            name = "Dodge Challenger",
            description = "Много лошадок делает врум-врум",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
        GoalModel(
            name = "Хата в Казани",
            description = "Ипотекаaaaaa",
            currentValue = 3_000_000,
            maxValue = 15_556_000
        ),
        GoalModel(
            name = "Dodge Challenger",
            description = "Много лошадок делает врум-врум",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
        GoalModel(
            name = "Dodge Challenger",
            description = "Много лошадок делает врум-врум",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
        GoalModel(
            name = "Dodge Challenger",
            description = "Много лошадок делает врум-врум",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
        GoalModel(
            name = "Dodge Challenger",
            description = "Много лошадок делает врум-врум",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
        GoalModel(
            name = "Dodge Challenger",
            description = "Много лошадок делает врум-врум",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = dimensionResource(R.dimen.vertical_screen_padding))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Цели",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )


            GoalsList(
                modifier = Modifier,
                goals = goals
            )




        }
        CustomButton(
            text = "Добавить цель",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
            onClick = {

            }
        )
    }
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GoalScreen()
        }
    }
}