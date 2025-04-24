package ru.practice.t_finance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.practice.t_finance.presentation.screens.authentication.AuthScreen
import ru.practice.t_finance.presentation.screens.authentication.ConsentCodeScreen
import ru.practice.t_finance.presentation.screens.authentication.InputNameScreen
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TfinanceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ){ padding ->
                    AuthNavigation(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun AuthNavigation(modifier: Modifier) {
    // Управление состоянием навигации
    var currentScreen by remember { mutableStateOf(AuthNavigationScreens.AuthScreen) }
    
    when (currentScreen) {
        AuthNavigationScreens.AuthScreen -> {
            AuthScreen(
                modifier = modifier,
            )
        }
        AuthNavigationScreens.ConsentCodeScreen -> {
            ConsentCodeScreen(
                modifier = modifier,
                onBackClick = { currentScreen = AuthNavigationScreens.AuthScreen },
                onNavigateToInputName = { currentScreen = AuthNavigationScreens.InputNameScreen }
            )
        }
        AuthNavigationScreens.InputNameScreen -> {
            InputNameScreen(
                modifier = modifier,
                onBackClick = { currentScreen = AuthNavigationScreens.ConsentCodeScreen },
                onRegistrationComplete = { 
                    // Здесь переход на главный экран после успешной регистрации
                    // Заглушка для примера
                    currentScreen = AuthNavigationScreens.AuthScreen
                }
            )
        }
    }
}

enum class AuthNavigationScreens {
    AuthScreen,
    ConsentCodeScreen,
    InputNameScreen
}
