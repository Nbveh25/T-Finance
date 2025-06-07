package ru.practice.t_finance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.domain.model.GetCategoryModel
import ru.practice.t_finance.presentation.model.GoalItem
import ru.practice.t_finance.presentation.navigation.Routes


@Composable
fun CustomProgressBar(
    modifier: Modifier,
    accumulatedAmount: Double,
    amount: Double = 3556.0,
    height: Dp = dimensionResource(R.dimen.padding_medium),
    cornerRadius: Dp = 12.dp
) {
    val progress = remember(accumulatedAmount, amount) {
        (accumulatedAmount / amount).coerceIn(0.0, 1.0)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.LightGray.copy(alpha = 0.3f))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(cornerRadius)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(height / 8)
                .fillMaxWidth(progress.toFloat())
                .fillMaxHeight()
                .clip(RoundedCornerShape(cornerRadius))
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun Goal(name: String, accumulatedAmount: Double, amount: Double, modifier: Modifier) {
    Column {
        Row(
            modifier = modifier
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = amount.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        CustomProgressBar(
            modifier = modifier,
            accumulatedAmount = accumulatedAmount,
            amount = amount
        )
    }
}

@Composable
fun GoalSlot(modifier: Modifier = Modifier, goalItemList: List<GoalItem>, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .shadow(
                elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = stringResource(R.string.goals),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            )
        )

        goalItemList.take(3).forEach { goal ->
            Goal(
                name = goal.name,
                accumulatedAmount = goal.accumulatedAmount,
                amount = goal.amount,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_small)
                ),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CustomButton(
                text = stringResource(R.string.more),
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun CustomSpinner(
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    items: List<GetCategoryModel> = emptyList(),
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val selectedCategory = items.find { it.name == value }

    Box(modifier = modifier.fillMaxWidth()) {
        // Основное поле спиннера
        Surface(
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium)),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showBottomSheet = true }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_medium)
                    )
            ) {


                // Текст (выбранное значение или плейсхолдер)
                Text(
                    text = selectedCategory?.name ?: placeholderText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )

                // Стрелка вниз
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown arrow",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    // BottomSheet для выбора категории
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = rememberModalBottomSheetState(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Text(
                        text = placeholderText,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                items(items.size) { category ->
                    Surface(
                        color = Color.Transparent,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onValueChange(items[category].name)
                            showBottomSheet = false
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        ) {
                            // Иконка категории
                            AsyncImage(
                                model = items[category].iconPath,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape),
                                colorFilter = ColorFilter.tint(items[category].color),
                                //placeholder = painterResource(R.drawable.ic_category_placeholder),
                                //error = painterResource(R.drawable.ic_category_placeholder),
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = items[category].name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Divider(modifier = Modifier.padding(start = 48.dp))
                }
            }
        }
    }
}
@Composable
fun GoalsList(
    modifier: Modifier = Modifier,
    goals: List<GoalItem>,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = dimensionResource(R.dimen.padding_extra_small)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        item {
            Text(
                text = stringResource(R.string.goals),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )
        }

        itemsIndexed(goals) { index, goal ->
            GoalCard(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_extra_small)
                ),
                name = goal.name,
                description = goal.description,
                amount = goal.amount,
                onClick = {
                    navController.navigate("${Routes.GOALS_DETAIL_SCREEN}/${goal.id}")
                }
            )
        }

    }
}

