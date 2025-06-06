package ru.practice.t_finance.presentation.screens.addingTransaction

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import ru.practice.t_finance.domain.model.GetCategoryModel
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomSpinner
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.components.DatePicker
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddingTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: AddingTransactionViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()

    var selectedValue by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Другой день") }

    var items = emptyList<GetCategoryModel>()
    LaunchedEffect(state.value) {
        if (state.value is AddingTransactionScreenState.Success) {
            items = viewModel.categories
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = dimensionResource(R.dimen.vertical_screen_padding))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            IconButton(
                modifier = Modifier,
                onClick = {}
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholderText = "Выберите категорию",
                items = viewModel.categories,
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
        CustomButton(
            text = stringResource(R.string.add),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
            onClick = {

            }
        )
    }
}

fun generateCategoryList(): List<GetCategoryModel> {
    return listOf(
        GetCategoryModel(
            id = 1,
            name = "Продукты",
            color = Color(0xFF4CAF50), // Зеленый
            icon = "https://cdn-icons-png.flaticon.com/512/2436/2436874.png"
        ),
        GetCategoryModel(
            id = 2,
            name = "Транспорт",
            color = Color(0xFF2196F3), // Синий
            icon = "https://cdn-icons-png.flaticon.com/512/2786/2786395.png"
        ),
        GetCategoryModel(
            id = 3,
            name = "Жилье",
            color = Color(0xFF9C27B0), // Фиолетовый
            icon = "https://cdn-icons-png.flaticon.com/512/2777/2777154.png"
        ),
        GetCategoryModel(
            id = 4,
            name = "Развлечения",
            color = Color(0xFFFF9800), // Оранжевый
            icon = "https://cdn-icons-png.flaticon.com/512/2936/2936886.png"
        ),
        GetCategoryModel(
            id = 5,
            name = "Здоровье",
            color = Color(0xFFE91E63), // Розовый
            icon = "https://cdn-icons-png.flaticon.com/512/2969/2969398.png"
        ),
        GetCategoryModel(
            id = 6,
            name = "Одежда",
            color = Color(0xFF00BCD4), // Голубой
            icon = "https://cdn-icons-png.flaticon.com/512/3081/3081985.png"
        ),
        GetCategoryModel(
            id = 7,
            name = "Образование",
            color = Color(0xFF795548), // Коричневый
            icon = "https://cdn-icons-png.flaticon.com/512/2936/2936886.png"
        ),
        GetCategoryModel(
            id = 8,
            name = "Подарки",
            color = Color(0xFFF44336), // Красный
            icon = "https://cdn-icons-png.flaticon.com/512/2583/2583344.png"
        ),
        GetCategoryModel(
            id = 9,
            name = "Техника",
            color = Color(0xFF607D8B), // Серо-голубой
            icon = "https://cdn-icons-png.flaticon.com/512/2921/2921222.png"
        ),
        GetCategoryModel(
            id = 10,
            name = "Другое",
            color = Color(0xFF9E9E9E), // Серый
            icon = "https://cdn-icons-png.flaticon.com/512/860/860828.png"
        )
    )
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //AddingTransaction()
        }
    }
}