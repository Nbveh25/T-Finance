package ru.practice.t_finance.presentation.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    primary = tFinancePrimary,
    secondary = tFinanceSecondary,
    background = tFinanceLightBackground,
    surface = tFinanceLightSurface,
    surfaceVariant = tFinanceLightSurfaceVariant,
    onSurfaceVariant = tFinanceLightOnSurfaceVariant,
    onBackground = tFinanceLightOnBackground,
    onSecondary = tFinanceLightOnSecondary,
    onPrimary = tFinanceLightOnPrimary,
    error = tFinanceError,
    onError = tFinanceOnError
)

private val DarkColors = darkColorScheme(
    primary = tFinancePrimary,
    secondary = tFinanceSecondary,
    background = tFinanceDarkBackground,
    surface = tFinanceDarkSurface,
    surfaceVariant = tFinanceDarkSurfaceVariant,
    onSurfaceVariant = tFinanceDarkOnSurfaceVariant,
    onBackground = tFinanceDarkOnBackground,
    onSecondary = tFinanceDarkOnSecondary,
    onPrimary = tFinanceDarkOnPrimary,
    error = tFinanceError,
    onError = tFinanceOnError
)

@Composable
fun TfinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}