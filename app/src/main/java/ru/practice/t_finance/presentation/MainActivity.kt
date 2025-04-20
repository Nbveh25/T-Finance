package ru.practice.t_finance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.practice.t_finance.presentation.screens.ColorTestScreen
import ru.practice.t_finance.presentation.theme.TfinanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TfinanceTheme {
                ColorTestScreen()
            }
        }
    }
}
