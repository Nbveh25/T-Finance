package ru.practice.t_finance.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.text.font.Font
import ru.practice.t_finance.presentation.theme.CalendarTypography
import java.time.YearMonth

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    centerText: Boolean = false,
    enabled: Boolean = true
) {
    val centeredTextStyle = if (centerText) {
        MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
    } else {
        MaterialTheme.typography.bodySmall
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = if (centerText) modifier.fillMaxWidth() else modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium)),
        placeholder = {
            Text(
                text = placeholderText,
                style = centeredTextStyle,
                modifier = if (centerText) Modifier.fillMaxWidth() else Modifier
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        textStyle = centeredTextStyle,
        visualTransformation = visualTransformation,
        enabled = enabled
    )
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium)),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                vertical = dimensionResource(R.dimen.padding_small)
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PickerButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    maxLines: Int = 1
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
            .shadow(
                elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium)),
                clip = false
            )
            .then(
                if (isSelected) Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium))
                ) else Modifier
            )
            .height(56.dp),

        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_medium)),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    selectableDates: SelectableDates
) {

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = selectableDates
    )
    MaterialTheme(typography = CalendarTypography) {
        DatePickerDialog(
            modifier = Modifier
                .shadow(
                    elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
                )
                .background(color = MaterialTheme.colorScheme.background),
            onDismissRequest = onDismiss,
            confirmButton = {
                CalendarButton(
                    text = stringResource(R.string.select),
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                        onDismiss()
                    },
                )
            },
            dismissButton = {
                CalendarButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    headlineContentColor = MaterialTheme.colorScheme.onBackground,
                    weekdayContentColor = MaterialTheme.colorScheme.onBackground,
                    subheadContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    dayContentColor = MaterialTheme.colorScheme.onBackground,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    selectedDayContentColor = MaterialTheme.colorScheme.onBackground,
                    disabledDayContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    //disabledDayContainerColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun CalendarButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent
        ),
        border = null,
        elevation = null
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    selectedDate: String = stringResource(R.string.another_day),
    onAnotherDayClick: (Long) -> Unit = {}
) {
    var selectedButton by remember { mutableIntStateOf(2) }
    var showCalendar by remember { mutableStateOf(false) }

    if (showCalendar) {
        CalendarBottomSheet(
            onDateSelected = { millis ->
                onAnotherDayClick(millis)
                showCalendar = false
            },
            selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun isSelectableYear(year: Int): Boolean {
                return year <= YearMonth.now().year
            }
        },
            onDismiss = { showCalendar = false }
        )
    }

    Row(modifier = modifier) {
        PickerButton(
            text = stringResource(R.string.yesterday),
            isSelected = selectedButton == 0,
            onClick = { selectedButton = 0 }
        )
        Spacer(modifier = Modifier.width(8.dp))
        PickerButton(
            text = stringResource(R.string.today),
            isSelected = selectedButton == 1,
            onClick = { selectedButton = 1 }
        )
        Spacer(modifier = Modifier.width(8.dp))
        PickerButton(
            text = selectedDate,
            isSelected = selectedButton == 2,
            onClick = {
                selectedButton = 2
                showCalendar = true
            },
            maxLines = 2
        )
    }
}

@Composable
fun BudgetDiagram(
    modifier: Modifier = Modifier,
    thickness: Dp = 35.dp,
    data: List<Category>
) {
    val thicknessPx = with(LocalDensity.current) { thickness.toPx() } //

    Canvas(
        modifier = modifier
            .width(600.dp)
            .height(400.dp)
    ) {
        val outerRadius = size.minDimension / 2.3f - thicknessPx
        val centerX = size.width / 2
        val centerY = size.height / 2

        drawArc(
            color = Color.Gray,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(thicknessPx),
            topLeft = Offset(centerX - outerRadius, centerY - outerRadius),
            size = Size(outerRadius * 2, outerRadius * 2)
        )

        var startAngle = 0f
        data.forEach { category ->
            val sweepAngle = category.value * 360f

            drawArc(
                color = category.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(thicknessPx),
                topLeft = Offset(centerX - outerRadius, centerY - outerRadius),
                size = Size(outerRadius * 2, outerRadius * 2)
            )

            startAngle += sweepAngle
        }
    }
}

@Composable
fun CategoryTile(category: Category, onClick: () -> Unit, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(category.color)
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                fontSize = 17.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Icon(
                imageVector = if (isSelected) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = if (isSelected) "Remove" else "Add",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun GoalCard(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    maxValue: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_shadow_elevation_medium)
        ),
        onClick = {

        }
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${maxValue} ₽",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small)))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun Transaction(
    iconUrl: String,
    transactionName: String,
    categoryName: String,
    summa: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = iconUrl,
                contentDescription = "icon_transaction",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = transactionName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_extra_small))
                )
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_extra_small))
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(48.dp)
        ) {
            Text(
                text = "-${summa} ₽",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_extra_small))
            )
            Text(
                text = stringResource(R.string.rubles),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_extra_small))
            )
        }
    }
}

@Composable
fun TransactionSlot(modifier: Modifier = Modifier, transactionModelList: List<TransactionModel>) {
    Card(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Text(
            text = stringResource(R.string.on_this_week),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            )
        )
        Transaction(
            iconUrl = transactionModelList[0].iconUrl,
            transactionName = transactionModelList[0].transactionName,
            categoryName = transactionModelList[0].categoryName,
            summa = transactionModelList[0].summa,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_extra_small)
            )
        )
        Transaction(
            iconUrl = transactionModelList[1].iconUrl,
            transactionName = transactionModelList[1].transactionName,
            categoryName = transactionModelList[1].categoryName,
            summa = transactionModelList[1].summa,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_extra_small)
            )
        )
        Transaction(
            iconUrl = transactionModelList[2].iconUrl,
            transactionName = transactionModelList[2].transactionName,
            categoryName = transactionModelList[2].categoryName,
            summa = transactionModelList[2].summa,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_extra_small)
            )
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
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GoalCard(
                name = "Dodge Challenger",
                description = "wrooom wroom",
                maxValue = 5555555,
                onClick = {}
            )
        }
    }
}