package ru.practice.t_finance.presentation.screens.goalList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.presentation.components.GoalsList
import ru.practice.t_finance.presentation.theme.TfinanceTheme

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
            description = "Много лошадок делает врум-врумааааааааааааааааааааааааааааааааааааааааааааааа",
            currentValue = 1_200_000,
            maxValue = 5_556_000
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            GoalsList(
                modifier = Modifier,
                goals = goals
            )

        }
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