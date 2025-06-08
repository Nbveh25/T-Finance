package ru.practice.t_finance.presentation.screens.addingTransaction

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.GetCategoryModel
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomSpinner
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.components.DatePicker
import ru.practice.t_finance.presentation.navigation.Routes
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
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    // Форматируем дату для отображения
    val formattedDate = remember(viewModel.selectedDate) {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            .format(viewModel.selectedDate)
    }

    var items = emptyList<GetCategoryModel>()

    LaunchedEffect(state.value) {
        if (state.value is AddingTransactionScreenState.Success && !(state.value as AddingTransactionScreenState.Success).navigation) {
            items = viewModel.categories
        }

        if (state.value is AddingTransactionScreenState.Success && (state.value as AddingTransactionScreenState.Success).navigation) {
            navController.navigate(route = Routes.MAIN_SCREEN)
        }

        if (state.value is AddingTransactionScreenState.Error) {
            viewModel.errorMessage = "Произошла ошибка"
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = dimensionResource(R.dimen.vertical_screen_padding))
    ) {
        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = stringResource(R.string.adding_transaction),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Поле для суммы
            CustomTextField(
                value = viewModel.amount,
                onValueChange = viewModel::updateAmount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholderText = stringResource(R.string.summa),
                keyboardType = KeyboardType.Number
            )

            // Поле для описания
            CustomTextField(
                value = viewModel.description,
                onValueChange = viewModel::updateDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholderText = "Описание",
                keyboardType = KeyboardType.Text
            )

            // Выбор категории
            CustomSpinner(
                value = selectedCategory,
                onValueChange = {
                    selectedCategory = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholderText = "Выберите категорию",
                items = viewModel.categories
            )

            // Выбор даты
            DatePicker(
                modifier = Modifier.padding(16.dp),
                selectedDate = formattedDate,
                onAnotherDayClick = { millis ->
                    viewModel.updateDate(Date(millis))
                }
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



        // Кнопка добавления
        CustomButton(
            text = stringResource(R.string.add),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = {
                Log.d("AddTraScreen", "Click")
                val foundCategory = viewModel.categories.firstOrNull() {it.name == selectedCategory}
                if (foundCategory != null) {
                    Log.d("AddTraScreen", "category found")
                    viewModel.addTransaction(foundCategory)
                } else {
                    viewModel.errorMessage = "Выберите категорию"
                }
            }
        )

        // BottomSheet для выбора категории
        if (showBottomSheet) {
            CategorySelectionBottomSheet(
                categories = viewModel.categories,
                onCategorySelected = {
                    showBottomSheet = false
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectionBottomSheet(
    categories: List<GetCategoryModel>,
    onCategorySelected: (GetCategoryModel) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(categories.size) { category ->
                CategoryItem(
                    category = categories[category],
                    onClick = { onCategorySelected(categories[category]) }
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: GetCategoryModel,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            AsyncImage(
                model = category.iconPath,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                colorFilter = ColorFilter.tint(category.color)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}