package ru.practice.t_finance.presentation.screens.authentication.consentCode

import androidx.compose.runtime.getValue
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
import androidx.compose.ui.Alignment
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
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.CustomButton
import ru.practice.t_finance.presentation.components.CustomTextField
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun ConsentCodeScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ConsentCodeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier,
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
            Text(
                text = stringResource(R.string.code_send_to, viewModel.phoneNumber),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                maxLines = 2
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))

            CustomTextField(
                value = viewModel.code,
                onValueChange = {
                    viewModel.updateCode(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                placeholderText = stringResource(R.string.code),
                keyboardType = KeyboardType.Number,
                centerText = true
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

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_extra_small)))

            CustomButton(
                text = stringResource(R.string.send),
                onClick = {
                    viewModel.sendCode()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_extra_small)))

        }
    }

    LaunchedEffect(state) {
        if (state is ConsentCodeState.Success) {
            navController.navigate(Routes.INPUT_NAME_SCREEN)
        }
    }

}

@Composable
@Preview
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //ConsentCodeScreen(Modifier)
        }
    }
}