package ru.practice.t_finance.presentation.screens.goalEdit

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CalendarBottomSheet
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalEditScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var showCalendar by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    if (showCalendar) {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        CalendarBottomSheet(
            onDateSelected = { millis ->
                selectedDate = formatter.format(millis)
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
                onClick = onBackClick
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
                text = "Редактирование цели",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                value = "",
                onValueChange = {

                },
                placeholderText = "Название"
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                value = "",
                onValueChange = {

                },
                placeholderText = "Описание"
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                value = "",
                onValueChange = {

                },
                placeholderText = "Сумма"
            )

            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .clickable { showCalendar = true },
                value = selectedDate,
                onValueChange = {},
                placeholderText = "Срок",
                enabled = false // Отключаем ручной ввод
            )
        }
        CustomButton(
            text = stringResource(R.string.save),
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
            GoalEditScreen(onBackClick = {})
        }
    }
}