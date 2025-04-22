package ru.practice.t_finance.presentation.screens.budgetAllocation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.repository.Categories
import ru.practice.t_finance.presentation.components.BudgetDiagram
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun BudgetAllocationScreen(
    modifier: Modifier,
    viewModel : CategoryViewModel = hiltViewModel()
    ){

    var selectedCategories by remember { mutableStateOf<List<Category>>(emptyList())}

    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)))
        Text(
            style = MaterialTheme.typography.displayLarge,
            text = "Распределите бюджет",
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)))
        BudgetDiagram(Modifier,
            data = Categories.categories
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))
        HorizontalDivider()
    }

    fun onCategorySelected(category: Category) {
        selectedCategories = selectedCategories + category
    }

    fun onCategoryDeselected(category: Category) {
        selectedCategories = selectedCategories.filterNot { it == category }
    }
}




@Preview
@Composable
private fun Preview(){
    TfinanceTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background
        ){ padding ->
            BudgetAllocationScreen(Modifier)
        }
    }
}