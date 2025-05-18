package ru.practice.t_finance.presentation.screens.authentication.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.util.PhoneNumberVisualTransformation
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 144.dp))

        Text(
            text = stringResource(R.string.t_finance_rus),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            fontSize = 36.sp,
            modifier = Modifier.padding(horizontal = 64.dp)
        )

        Spacer(modifier = Modifier.padding(top = 44.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.input_phone_number),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)))

            CustomTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
                placeholderText = stringResource(R.string.phone_number),
                keyboardType = KeyboardType.Phone,
                visualTransformation = PhoneNumberVisualTransformation(),
                enabled = state !is AuthScreenState.Loading
            )

            // Ощибки мында
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

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_extra_small)))

            CustomButton(
                text = stringResource(R.string.next),
                onClick = { viewModel.sendCode() },
                enabled = state !is AuthScreenState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
            )

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Image(
                painter = painterResource(R.drawable.img_tbank),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 80.dp)
            )
        }
    }

    LaunchedEffect(state) {
        if (state is AuthScreenState.Success) {
            navController.navigate("${Routes.CONSENT_CODE_SCREEN}?phoneNumber=+7${viewModel.phoneNumber}")
        }
    }

}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //AuthScreen()
        }
    }
}