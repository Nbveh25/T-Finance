package ru.practice.t_finance.presentation

import CustomBottomAppBar
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practice.t_finance.app.NotificationHandler
import ru.practice.t_finance.app.PermissionHandler
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.token.TokenService
import ru.practice.t_finance.presentation.navigation.AppNavigation
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var notificationHandler: NotificationHandler

    @Inject
    lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TfinanceTheme {
                val navController = rememberNavController()
                val tokenService = remember { TokenService(apiService = apiService, context = applicationContext) }
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                // Проверяем наличие активного токена
                val hasValidToken = remember {
                    tokenService.getAccessToken() != null
                }

                val showBottomBar = currentRoute in listOf(
                    Routes.MAIN_SCREEN,
                    Routes.BUDGET_SCREEN,
                    Routes.GOALS_SCREEN,
                    Routes.MORE_SCREEN,
                    Routes.ADD_SCREEN
                )

                LaunchedEffect(hasValidToken) {
                    // Навигация в зависимости от наличия токена
                    if (!hasValidToken) {
                        navController.navigate(Routes.AUTH_SCREEN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                }

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
                            startDestination = if (hasValidToken) Routes.MAIN_SCREEN else Routes.AUTH_SCREEN
                        )
                    }
                }
            }
        }
        if (!permissionHandler.isNotificationPermissionGranted()) {
            permissionHandler.requestNotificationPermission(this) { granted ->
                if (granted) {
                    notificationHandler.createNotificationChannel()
                } else {
                    Toast.makeText(this, "Разрешение на уведомления не получены", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Firebase.crashlytics.setCustomKeys{
            val id = UUID.randomUUID()
            key("userId","$id")
        }

    }
}