package ru.practice.t_finance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun CustomProgressBar(
    modifier: Modifier,
    currentValue: Int,
    maxValue: Int = 3556,
    height: Dp = dimensionResource(R.dimen.padding_medium),
    cornerRadius: Dp = 12.dp
) {
    val progress = remember(currentValue, maxValue) {
        (currentValue.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f)
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
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(cornerRadius))
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun Goal(name: String, currentValue: Int, maxValue: Int, modifier: Modifier) {
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
                text = maxValue.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        CustomProgressBar(
            modifier = modifier,
            currentValue = currentValue,
            maxValue = maxValue
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
            currentValue = goalModelList[0].currentValue,
            maxValue = goalModelList[0].maxValue,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Goal(
            name = goalModelList[1].name,
            currentValue = goalModelList[1].currentValue,
            maxValue = goalModelList[1].maxValue,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Goal(
            name = goalModelList[2].name,
            currentValue = goalModelList[2].currentValue,
            maxValue = goalModelList[2].maxValue,
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
    goals: List<GoalModel>
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
                maxValue = goal.currentValue,
                onClick = {}
            )
        }

        item {
            CustomButton(
                text = stringResource(R.string.add_goal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
                onClick = {

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