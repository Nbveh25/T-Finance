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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.navigation.Routes
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun CustomBottomAppBar(
    navController: NavController,
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth().height(124.dp)
            .background(Color.Transparent),
        tonalElevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
        containerColor = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                NavItem(
                    title = stringResource(R.string.main),
                    iconRes = R.drawable.ic_main,
                    route = Routes.MAIN_SCREEN,
                    onItemClick = {
                        navController.navigate(Routes.MAIN_SCREEN)
                    }
                )
                NavItem(
                    title = stringResource(R.string.budget),
                    iconRes = R.drawable.ic_budget,
                    route = Routes.BUDGET_SCREEN,
                    onItemClick = {
                        navController.navigate(Routes.BUDGET_SCREEN)
                    }
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
                            tint = MaterialTheme.colorScheme.background
                        )
                    }

                }

                NavItem(
                    title = stringResource(R.string.goals),
                    iconRes = R.drawable.ic_goal,
                    route = Routes.GOALS_SCREEN,
                    onItemClick = {
                        navController.navigate(Routes.GOALS_SCREEN)
                    }
                )
                NavItem(
                    title = stringResource(R.string.echo),
                    iconRes = R.drawable.ic_more,
                    route = Routes.MORE_SCREEN,
                    onItemClick = {
                        navController.navigate(Routes.MORE_SCREEN)
                    }
                )

            }
        }
    }
}

@Composable
fun NavItem(
    title: String,
    iconRes: Int,
    route: String,
    onItemClick: (String) -> Unit,
    iconTint: Color = MaterialTheme.colorScheme.onSurface,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(64.dp)
            .clickable { onItemClick(route) }
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = iconTint,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 2.dp),
            maxLines = 1,
            color = textColor,
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