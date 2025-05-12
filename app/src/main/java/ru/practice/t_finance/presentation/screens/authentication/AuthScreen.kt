package ru.practice.t_finance.presentation.screens.authentication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.util.ResponseResult
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.io.IOException

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 144.dp))

        Image(
            painter = painterResource(R.drawable.tfinance_logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 64.dp)
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
                onValueChange = {
                    viewModel.updatePhoneNumber(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
                placeholderText = stringResource(R.string.phone_number),
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_extra_small)))

            CustomButton(
                text = stringResource(R.string.next),
                onClick = {
                    scope.launch {
                        viewModel.sendCode().collect { result ->
                            when (result) {
                                is ResponseResult.Success -> {
                                    if (result.data.code() == 204) {
                                        navController.navigate("verification")
                                    } else {
                                        snackbarHostState.showSnackbar(
                                            //message = result.data.message() ?: "Ошибка отправки кода"
                                            result.data.code().toString()
                                        )
                                    }
                                }

                                is ResponseResult.Failure -> {
                                    val message = when (result.error) {
                                        is IllegalArgumentException -> "Неверный формат номера телефона"
                                        is IOException -> "Ошибка сети. Проверьте подключение"
                                        else -> "Произошла ошибка. Попробуйте позже"
                                    }
                                    //snackbarHostState.showSnackbar(message)
                                }

                                is ResponseResult.Loading -> {
                                    // Загрузка
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),

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

    SnackbarHost(
        hostState = snackbarHostState,
    )
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