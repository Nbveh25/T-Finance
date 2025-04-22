package ru.practice.t_finance.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun ConsentCodeScreen(modifier: Modifier) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        IconButton(
            modifier = Modifier,
            onClick = {

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

        // Header
        Text(
            text = stringResource(R.string.confirmation),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
        )

        Spacer(modifier = Modifier.padding(vertical = 96.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Content
            Text(
                text = "Код отправлен на номер\n      +7 927 950 06 74", //потом регулярку сделаем
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))

            CustomTextField(
                value = code,
                onValueChange = { code = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                placeholderText = "Код",
                keyboardType = KeyboardType.Number,
                centerText = true
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_extra_small)))

            CustomButton(
                text = stringResource(R.string.send_code_again),
                onClick = { /* Обработка нажатия */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
            )
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