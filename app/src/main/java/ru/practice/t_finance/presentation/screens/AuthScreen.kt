package ru.practice.t_finance.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun AuthScreen(
    modifier: Modifier
) {
    var phoneNumber by remember { mutableStateOf("") }
    val isButtonEnabled = phoneNumber.isNotBlank() // Простая валидация

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 48.dp))

        Text(
            text = stringResource(R.string.t_finance),
            style = MaterialTheme.typography.displayLarge,
        )

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_extra_large)))

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.input_phone_number),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_large)))

            CustomTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(R.string.phone_number),
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_extra_small)))

            CustomButton(
                text = stringResource(R.string.next),
                onClick = { /* Обработка нажатия */ },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            )
        }
    }
}
@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(Modifier)
        }
    }
}