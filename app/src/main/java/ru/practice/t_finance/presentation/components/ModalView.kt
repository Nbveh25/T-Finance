package ru.practice.t_finance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
fun Goal(goalName: String, currentValue: Int, maxValue: Int, modifier: Modifier) {
    Column {
        Row(
            modifier = modifier
        ) {
            Text(
                text = goalName,
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
                elevation = dimensionResource(R.dimen.card_shadow_elevation),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
            goalName = goalModelList[0].goalName,
            currentValue = goalModelList[0].currentValue,
            maxValue = goalModelList[0].maxValue,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Goal(
            goalName = goalModelList[1].goalName,
            currentValue = goalModelList[1].currentValue,
            maxValue = goalModelList[1].maxValue,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        )
        Goal(
            goalName = goalModelList[2].goalName,
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

@Preview
@Composable
fun PreviewCustomProgressBar() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GoalSlot(
                modifier = Modifier,
                goalModelList = listOf(
                    GoalModel("Dodge Challenger", currentValue = 12_000, maxValue = 7_500_000),
                    GoalModel("Ford Mustang", currentValue = 12_000, maxValue = 7_500_000),
                    GoalModel("Lamborghini Countach", currentValue = 12_000, maxValue = 7_500_000)
                )
            )
        }
    }
}