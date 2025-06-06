package ru.practice.t_finance.presentation.screens.goal.goalCreate

import android.os.Build
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
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalCreateScreen(
    modifier: Modifier = Modifier,
    viewModel: GoalCreateViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()

    var showCalendar by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    if (showCalendar) {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        CalendarBottomSheet(
            onDateSelected = { millis ->
                selectedDate = formatter.format(millis)
                viewModel.updateTerm(selectedDate)
                showCalendar = false
            },
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= System.currentTimeMillis()
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun isSelectableYear(year: Int): Boolean {
                    return year >= YearMonth.now().year
                }
            },
            onDismiss = { showCalendar = false }
        )
    }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            IconButton(
                modifier = Modifier,
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Назад",
                    modifier = Modifier
                        .padding(start = dimensionResource(R.dimen.padding_small))
                        .size(96.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )

            }

            Text(
                text = stringResource(R.string.goal_edit),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                value = viewModel.name,
                onValueChange = {
                    viewModel.updateName(it)
                },
                placeholderText = stringResource(R.string.naming)
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                value = viewModel.description,
                onValueChange = {
                    viewModel.updateDescription(it)
                },
                placeholderText = stringResource(R.string.description)
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                value = viewModel.amount.toString(),
                onValueChange = {
                    viewModel.updateAmount(it)
                },
                placeholderText = stringResource(R.string.summa),
                keyboardType = KeyboardType.Number
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .clickable { showCalendar = true },
                value = selectedDate,
                onValueChange = {},
                placeholderText = stringResource(R.string.term),
                enabled = false
            )

            if (viewModel.errorMessage != null) {
                Text(
                    text = viewModel.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
                        .padding(top = 4.dp)
                )
            }
        }
        CustomButton(
            text = stringResource(R.string.save),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
            onClick = {
                viewModel.createGoal()
            },
        )
    }

    LaunchedEffect(state.value) {
        if (state.value is GoalCreateScreenState.Success) {
            navController.navigate(route = Routes.GOALS_SCREEN)
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