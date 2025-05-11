package ru.practice.t_finance.presentation.screens.budgetAllocation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.theme.TfinanceTheme


@Composable
fun BudgetInputScreen(
    modifier: Modifier = Modifier,
    navController: NavController
){

    var budget by remember { mutableStateOf("")}
    val isButtonEnabled = budget.isNotBlank()


    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .fillMaxSize()
        ){
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large)))
        Text(
            style = MaterialTheme.typography.displayLarge,
            text = stringResource(R.string.input_budget),
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large)))
        CustomTextField(
            value = budget,
            onValueChange = { budget = it},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            placeholderText = "Сумма дохода",
            keyboardType = KeyboardType.Number,
            singleLine = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            text = stringResource(R.string.next),
            onClick = {
                val amount = budget.toLongOrNull() ?: 0L
                viewModel.setBudgetAmount(amount)
//                navController.navigate("budget_allocation_screen")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))
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
            //BudgetInputScreen(Modifier.padding(padding))
        }
    }
}