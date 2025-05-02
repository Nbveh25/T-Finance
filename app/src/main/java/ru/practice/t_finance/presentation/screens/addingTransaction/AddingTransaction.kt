package ru.practice.t_finance.presentation.screens.addingTransaction

import androidx.compose.foundation.layout.Column
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
import ru.practice.t_finance.presentation.components.CustomSpinner
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.components.DatePicker
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddingTransaction(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    var selectedValue by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Другой день") }
    val items = listOf("Продукты", "Коммунальные услуги", "Развлечения", "Транспорт", "Накопления", "Остальное")

    Column(
        modifier = modifier
            .fillMaxSize()
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
            text = stringResource(R.string.adding_transaction),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
        )

        CustomTextField(
            value = "",
            onValueChange = {
                //viewModel.updatePhoneNumber(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.horizontal_screen_padding)),
            placeholderText = stringResource(R.string.summa),
            keyboardType = KeyboardType.Number,
        )


        CustomSpinner(
            value = selectedValue,
            onValueChange = { selectedValue = it },
            items = items,
            placeholderText = stringResource(R.string.category),
            modifier = Modifier.padding(dimensionResource(R.dimen.horizontal_screen_padding))
        )

        DatePicker(
            modifier = Modifier.padding(dimensionResource(R.dimen.horizontal_screen_padding)),
            selectedDate = selectedDate,
            onAnotherDayClick = { millis ->
                val date = Date(millis)
                val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                selectedDate = formatter.format(date)
            }
        )
    }
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddingTransaction()
        }
    }
}