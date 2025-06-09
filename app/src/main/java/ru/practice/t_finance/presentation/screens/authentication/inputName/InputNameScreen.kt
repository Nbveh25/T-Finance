package ru.practice.t_finance.presentation.screens.authentication.inputName

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import okhttp3.Route
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.screens.authentication.auth.AuthViewModel
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun InputNameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: InputNameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
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

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(R.string.input_your_name),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))

            CustomTextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.updateName(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                placeholderText = stringResource(R.string.name),
                keyboardType = KeyboardType.Text,
            )
        }

        CustomButton(
            text = stringResource(R.string.next),
            onClick = {
                viewModel.sendName()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_large)
                ),
            enabled = viewModel.name.isNotBlank()
        )
    }

    LaunchedEffect(state) {
        if (state is InputNameScreenState.Success) {
            navController.navigate(Routes.BUDGET_SCREEN)
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //InputNameScreen(Modifier)
        }
    }
}