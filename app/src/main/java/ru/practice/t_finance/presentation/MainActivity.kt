package ru.practice.t_finance.presentation

import CustomBottomAppBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.practice.t_finance.presentation.navigation.AppNavigation
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TfinanceTheme {
                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                val showBottomBar =
                    currentRoute in listOf(
                        Routes.MAIN_SCREEN,
                        Routes.BUDGET_SCREEN,
                        Routes.GOALS_SCREEN,
                        Routes.MORE_SCREEN,
                        Routes.ADD_SCREEN
                    )
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    bottomBar = {
                        if (showBottomBar) {
                            CustomBottomAppBar(
                                navController = navController
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier.padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation(
                            navController = navController,
                            startDestination = Routes.EXPENSES_SCREEN
                            //startDestination = Routes.MAIN_SCREEN
                        )
                    }
                }

            }
        }
    }
}

