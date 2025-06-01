package ru.practice.t_finance.presentation.screens.budgetAllocation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.presentation.components.BudgetDiagram
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.practice.t_finance.presentation.components.AvailableCategories
import ru.practice.t_finance.presentation.components.BudgetAllocationInputField
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.SelectedCategories


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetAllocationScreen(
    modifier: Modifier = Modifier,
    viewModel : BudgetViewModel = hiltViewModel(),
    navController: NavController
    ){

    val categoryState by viewModel.categoryState.collectAsState()
    val categoryForEdit by viewModel.selectedCategoryForEdit.collectAsState()
    val remainingBudget by viewModel.remainingBudget.collectAsState()
    val elementaryBudget by viewModel.elementaryBudget.collectAsState()

    val isBudgetFullyAllocated by viewModel.isBudgetFullyAllocated


    val arguments = navController.currentBackStackEntry?.arguments


    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()


    val scrollState = rememberScrollState()


    LaunchedEffect(Unit) {
        viewModel.getCategories()
        viewModel.setElementaryBudget(arguments?.getString("budget","") ?: "")
    }

    val selectedCategories by viewModel.selectedCategories

    val selectedCategoriesList by remember {
        derivedStateOf {
            selectedCategories.values.toList()
        }
    }



    when(categoryState){
        is CategoryState.Loading -> {
            Text(text = "Loading")
        }
        is CategoryState.Success -> {
            val allCategories = (categoryState as CategoryState.Success).categories
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .fillMaxSize()
            ){
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)))
                Text(
                    style = MaterialTheme.typography.displayLarge,
                    text = stringResource(R.string.allocate_budget),
                )
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
                BudgetDiagram(Modifier,
                    data = selectedCategoriesList
                )
                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))
                SelectedCategories(
                    selectedCategories = selectedCategoriesList,
                    onCategoryDeselected = { category ->
                        viewModel.deleteCategory(category)

                    }
                )

                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))

                HorizontalDivider(
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))

                AvailableCategories(
                    categories = allCategories,
                    selectedCategories = selectedCategoriesList,
                    onCategorySelected = { category ->
                        viewModel.openBottomSheetFor(category)
                        showBottomSheet = true
                    }
                )
                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large)))
                CustomButton(
                    text = stringResource(R.string.next),
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isBudgetFullyAllocated
                )
            }
        }
        is CategoryState.Error -> {
            val errorMessage = (categoryState as CategoryState.Error).message
            Text(text = "Error: $errorMessage", color = Color.Red)
        }
    }



    if (showBottomSheet && categoryForEdit != null){

        ModalBottomSheet(
            onDismissRequest = {showBottomSheet = false},
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            PercentageBottomSheet(
                category = categoryForEdit!!,
                onSave = { category, newValue ->
                    viewModel.addToSelectedCategories(category,newValue.toInt())
                    showBottomSheet = false
                    viewModel.closeBottomSheet()
                },
                elementaryBudget = elementaryBudget,
                remainingBudget = remainingBudget
            )
        }

    }

}

fun calculateAllocatedAmount(budget: Int, percent: Int): Int {
    return kotlin.math.ceil((budget * percent) / 100.0).toInt()
}

@Composable
fun PercentageBottomSheet(
    category: Category,
    onSave : (category: Category,percent: Int) -> Unit,
    elementaryBudget: Int,
    remainingBudget: Int
){

    var percent by rememberSaveable { mutableStateOf(category.value.takeIf { it > 0 }?.toString() ?: "") }

    val percentValue = percent.toIntOrNull()

    val allocatedAmount = if (percentValue != null) {
        calculateAllocatedAmount(elementaryBudget, percentValue)
    } else {
        0
    }

    val newRemaining = remainingBudget - allocatedAmount


    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    val maxPercent = if (elementaryBudget > 0) {
        (remainingBudget.toDouble() / elementaryBudget.toDouble() * 100).toInt()
    } else {
        0
    }

    val remainingPercent = maxPercent - (percent.toIntOrNull() ?: 0)

    val isButtonEnabled by remember {
        derivedStateOf {
            percent.toIntOrNull() != null && percent.toInt() in 1..maxPercent
        }
    }

    val enterValue = stringResource(R.string.enter_value)
    val enterNumber = stringResource(R.string.enter_number)
    val valueMustBeGreaterThanZero = stringResource(R.string.value_must_be_greater_than_zero)
    val maxPercentString = stringResource(R.string.max_percent, maxPercent)

    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 24.sp
            ),
            text = stringResource(R.string.choose_percent),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 24.sp
            ),
            text = category.name,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BudgetAllocationInputField(
            value = percent,
            onValueChange = { newValue ->

                if (newValue.length > 3) return@BudgetAllocationInputField


                percent = newValue
                when {
                    newValue.isBlank() -> errorMessage = enterValue
                    newValue.toIntOrNull() == null -> errorMessage = enterNumber
                    newValue.toInt() == 0 -> errorMessage = valueMustBeGreaterThanZero
                    newValue.toInt() > maxPercent -> errorMessage = maxPercentString
                    else -> errorMessage = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Введите число",
            keyboardType = KeyboardType.Number,
            remainedText = remainingPercent.toString()
        )

        Spacer(modifier = Modifier.padding(4.dp))
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(R.string.this_string),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.remaining_string),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$allocatedAmount ₽",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            HorizontalDivider(
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.Center),
                thickness = 2.dp
            )

            Text(
                text = "$newRemaining ₽",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))

        CustomButton(
            text = stringResource(R.string.next),
            onClick = {
                val percentValue = percent.toIntOrNull()
                if (percentValue != null && errorMessage == null) {
                    onSave(category, percentValue)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        )
    }
}




//
//@Preview
//@Composable
//private fun Preview(){
//    TfinanceTheme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            containerColor = MaterialTheme.colorScheme.background
//        ){ padding ->
//            BudgetAllocationScreen(Modifier.padding(padding), BudgetViewModel(
//                BudgetUseCase(
//                    CategoryRepositoryImpl()
//                )
//            ))
//        }
//    }
//}


