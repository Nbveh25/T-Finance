package ru.practice.t_finance.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.GoalModel
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
fun GoalSlot(modifier: Modifier = Modifier, goalModelList: List<GoalModel>) {
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
        Goal(
            name = goalModelList[0].name,
            accumulatedAmount = goalModelList[0].accumulatedAmount,
            amount = goalModelList[0].amount,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Goal(
            name = goalModelList[1].name,
            accumulatedAmount = goalModelList[1].accumulatedAmount,
            amount = goalModelList[1].amount,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Goal(
            name = goalModelList[2].name,
            accumulatedAmount = goalModelList[2].accumulatedAmount,
            amount = goalModelList[2].amount,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CustomButton(
                text = stringResource(R.string.more),
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun CustomSpinner(
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    items: List<String> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium))
                )
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_medium)
                )
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown arrow",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium))
            )
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun GoalsList(
    modifier: Modifier = Modifier,
    goals: List<GoalModel>,
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
                    val goalJson = Gson().toJson(goal)
                    navController.navigate(Routes.GOALS_DETAIL_SCREEN + "/$goalJson")
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CustomSpinnerPreview() {
    var selectedValue by remember { mutableStateOf("") }
    val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomSpinner(
                value = selectedValue,
                onValueChange = { selectedValue = it },
                items = items,
                placeholderText = "Select an option",
            )
        }
    }
}