package ru.practice.t_finance.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun ConsentCodeScreen(modifier: Modifier) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.padding(top = 48.dp))
        // Header
        Text(
            text = stringResource(R.string.t_finance),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_extra_large)))

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Content
            Text(
                text = "Код отправлен на номер +7 927 950 06 74",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding()
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_large)))

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape)),
                placeholder = {
                    Text(
                        text = stringResource(R.string.phone_number),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = TextFieldDefaults.colors(
                    // Цвета фона
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, // Фон при фокусе
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, // Фон без фокуса
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant, // Фон в disabled состоянии

                    // Цвета границ (которые вы уже сделали прозрачными)
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    // Остальные цвета
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                textStyle = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_extra_small)))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape)),
                onClick = { /* ... */ }
            ) {
                Text(
                    modifier = Modifier.padding(
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                    text = stringResource(R.string.next),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ConsentCodeScreen(Modifier)
        }
    }
}