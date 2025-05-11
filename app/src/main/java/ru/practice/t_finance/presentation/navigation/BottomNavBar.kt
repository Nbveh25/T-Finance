import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun CustomBottomAppBar(
    navController: NavController,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                NavItem(
                    title = stringResource(R.string.main),
                    iconRes = R.drawable.ic_main,
                    onItemClick = {
                        navController.navigate(Routes.MAIN_SCREEN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    isActive = currentRoute == Routes.MAIN_SCREEN
                )
                NavItem(
                    title = stringResource(R.string.budget),
                    iconRes = R.drawable.ic_budget,
                    onItemClick = {
                        navController.navigate(Routes.BUDGET_SCREEN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    isActive = currentRoute == Routes.BUDGET_SCREEN
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(64.dp)
                        .padding(bottom = dimensionResource(R.dimen.padding_medium))
                ) {
                    FloatingActionButton(
                        modifier = Modifier.size(56.dp),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_extra_large)),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        onClick = {
                            navController.navigate(Routes.ADD_SCREEN)
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Добавить",
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }

                }

                NavItem(
                    title = stringResource(R.string.goals),
                    iconRes = R.drawable.ic_goal,
                    onItemClick = {
                        navController.navigate(Routes.GOALS_SCREEN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    isActive = currentRoute == Routes.GOALS_SCREEN
                )
                NavItem(
                    title = stringResource(R.string.echo),
                    iconRes = R.drawable.ic_more,
                    onItemClick = {
                        navController.navigate(Routes.MORE_SCREEN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    isActive = currentRoute == Routes.MORE_SCREEN
                )

            }
        }
    }
}

@Composable
fun NavItem(
    title: String,
    iconRes: Int,
    onItemClick: () -> Unit,
    isActive: Boolean = false,
) {
    val activeColor = MaterialTheme.colorScheme.secondary
    val inactiveColor = MaterialTheme.colorScheme.onSurface

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(74.dp)
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
            .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_extra_large)))
            .clickable {
                onItemClick()
            }
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = if (isActive) activeColor else inactiveColor,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 2.dp),
            maxLines = 1,
            color = if (isActive) activeColor else inactiveColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    TfinanceTheme {
        Scaffold(
            bottomBar = {
                CustomBottomAppBar(navController = rememberNavController())
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
            }
        }

    }
}