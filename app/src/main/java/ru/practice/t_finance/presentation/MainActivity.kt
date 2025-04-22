package ru.practice.t_finance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.practice.t_finance.presentation.screens.AuthScreen
import ru.practice.t_finance.presentation.screens.ColorTestScreen
import ru.practice.t_finance.presentation.screens.ConsentCodeScreen
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
                    AuthScreen(modifier = Modifier.padding(padding))
//                    ConsentCodeScreen(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}
