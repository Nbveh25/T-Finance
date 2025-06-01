package ru.practice.t_finance.presentation.components

import android.graphics.BlendMode
import android.graphics.BlurMaskFilter
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practice.t_finance.domain.model.Category
import kotlin.collections.forEach
import android.os.Build
import android.util.Log
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.TransactionModel
import ru.practice.t_finance.presentation.theme.TfinanceTheme
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDefaults.dateFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.internal.updateLiveLiteralValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import ru.practice.t_finance.presentation.model.TransactionListItem

import ru.practice.t_finance.presentation.theme.CalendarTypography
import java.nio.file.WatchEvent
import java.sql.Time
import java.time.YearMonth
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun BudgetDiagram(
    modifier: Modifier = Modifier,
    thickness: Dp = 35.dp,
    data: List<Category>
) {
    val thicknessPx = with(LocalDensity.current) { thickness.toPx() } //
    val density = LocalDensity.current
    val textColor = MaterialTheme.colorScheme.onBackground


    val sweepAngles = rememberSaveable(data) {
        data.map { category ->
            Animatable(category.value * 3.6f * 0f)
        }
    }

    val percentValues = rememberSaveable(data) {
        data.map { category ->
            Animatable(0f)
        }
    }

    LaunchedEffect(data) {
        val total = data.sumOf { it.value }.toFloat()


        data.forEachIndexed { index, category ->
            val targetAngle = category.value * 3.6f
            val targetPercent = category.value * 1f

            launch {
                sweepAngles[index].animateTo(targetAngle, animationSpec = tween(1000, easing = FastOutSlowInEasing))
            }
            launch {
                percentValues[index].animateTo(targetPercent, animationSpec = tween(1000, easing = FastOutSlowInEasing))
            }
        }
    }

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
            size = androidx.compose.ui.geometry.Size(outerRadius * 2, outerRadius * 2)
        )

        var startAngle = 270F



        data.forEachIndexed { index, category ->
            val animatedSweep = sweepAngles[index].value
            val animatedPercent = percentValues[index].value

            // Рассчитываем средний угол для размещения текста
            val halfAngle = startAngle + animatedSweep / 2
            val angleInRadians = Math.toRadians(halfAngle.toDouble()).toFloat()

            // Позиция текста — немного за пределами диаграммы
            val radius = outerRadius + thicknessPx + 10
            val textX = centerX + radius * cos(angleInRadians)
            val textY = centerY + radius * sin(angleInRadians)

            // Рисуем текст
            drawContext.canvas.nativeCanvas.drawText(
                "${animatedPercent.toInt()}%",
                textX,
                textY,
                android.graphics.Paint().apply {
                    textSize = 12.sp.toPx()
                    color = textColor.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                }
            )


            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint()
                paint.style = android.graphics.Paint.Style.STROKE
                paint.strokeWidth = thicknessPx
                paint.color = category.color.toArgb()
                paint.maskFilter = BlurMaskFilter(thicknessPx / 4, BlurMaskFilter.Blur.SOLID)

                drawArc(
                    RectF(
                        centerX - outerRadius,
                        centerY - outerRadius,
                        centerX + outerRadius,
                        centerY + outerRadius
                    ),
                    startAngle,
                    animatedSweep,
                    false,
                    paint
                )
            }

            drawArc(
                color = category.color,
                startAngle = startAngle,
                sweepAngle = animatedSweep,
                useCenter = false,
                style = Stroke(thicknessPx),
                topLeft = Offset(centerX - outerRadius, centerY - outerRadius),
                size = Size(outerRadius * 2, outerRadius * 2)
            )



            startAngle += animatedSweep
        }

    }
}

@Composable
fun EmptyDiagram(
    modifier: Modifier = Modifier,
    thickness: Dp = 35.dp,
){
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
            size = androidx.compose.ui.geometry.Size(outerRadius * 2, outerRadius * 2)
        )

    }
}

@Composable
fun CategoryTile(category: Category, onClick: () -> Unit, isSelected: Boolean){
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(category.color)
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 8.dp),
    ){
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
fun SelectedCategories(
    modifier: Modifier = Modifier,
    selectedCategories: List<Category>,
    onCategoryDeselected: (Category) -> Unit
) {
    val scrollableState = rememberScrollState()

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .scrollable(scrollableState, Orientation.Vertical),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        selectedCategories.forEach { category ->
            CategoryTile(
                category = category,
                onClick = { onCategoryDeselected(category) },
                isSelected = true
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AvailableCategories(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedCategories: List<Category>,
    onCategorySelected: (Category) -> Unit
) {

    val scrollableState = rememberScrollState()


    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .scrollable(
                scrollableState, Orientation.Vertical
            ),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        categories.forEach { category ->
            if (!selectedCategories.contains(category)) {
                val currentCategory = category.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                CategoryTile(
                    category = currentCategory,
                    onClick = { onCategorySelected(category) },
                    isSelected = false
                )
            }
        }
    }
}

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
                )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesDateRangePicker(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
){


    val dateRangePickerState = rememberDateRangePickerState()


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
                        val start = dateRangePickerState.selectedStartDateMillis
                        val end = dateRangePickerState.selectedEndDateMillis
                        onDateRangeSelected(Pair<Long,Long>(start as Long, end as Long))
                    },
                )
            },
            dismissButton = {
                CalendarButton(
                    text = stringResource(R.string.cancel),
                    onClick = {
                        onDismiss.invoke()
                    },
                )
            },
        ){
            DateRangePicker(
                modifier = Modifier
                    .fillMaxWidth(),
                state = dateRangePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    headlineContentColor = MaterialTheme.colorScheme.onBackground,
                    weekdayContentColor = MaterialTheme.colorScheme.onBackground,
                    subheadContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    dayContentColor = MaterialTheme.colorScheme.onBackground,
                    selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedDayContentColor = MaterialTheme.colorScheme.onSecondary,
                    disabledDayContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    //disabledDayContainerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        stringResource(R.string.choose_period),
                        modifier = Modifier.padding(16.dp)
                        )
                },

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
fun GoalCard(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    amount: Double,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_shadow_elevation_medium)
        )
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
                    text = "${amount} ₽",
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
fun TransactionsSlotExpenses(modifier: Modifier = Modifier, transactionModelList: List<TransactionListItem>) {
    Card(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )

    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            transactionModelList.forEach { data ->
                item{
                    Transaction(
                        iconUrl = data.imageUrl.toString(),
                        transactionName = data.name,
                        categoryName = data.category,
                        summa = kotlin.math.ceil(data.amountFormatted.toDouble()).toInt()
                    )
                }
            }
        }

    }
}


@Composable
fun TransactionSlot(modifier: Modifier = Modifier, transactionModelList: List<TransactionListItem>) {
    Card(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
            iconUrl = transactionModelList[0].imageUrl.toString(),
            transactionName = transactionModelList[0].name,
            categoryName = transactionModelList[0].name,
            summa = transactionModelList[0].amountFormatted.toInt(),
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

@Composable
fun CashbackBonusCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(
                vertical = dimensionResource(R.dimen.padding_small),
                horizontal = dimensionResource(R.dimen.padding_medium)
            ),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFA1F936)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_shadow_elevation_medium)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_medium)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.t),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Card(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(R.string.pro),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,

                    )
                }
            }
            Card(
                modifier = Modifier.padding(horizontal = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
                    text = stringResource(R.string.more),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        Text(
            modifier = Modifier.padding(
                vertical = dimensionResource(R.dimen.padding_small),
                horizontal = dimensionResource(R.dimen.padding_medium)
            ),
            text = stringResource(R.string.lock_stock_2_smoking_barrels),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun MoreCard(modifier: Modifier = Modifier, name: String, desc: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(
                vertical = dimensionResource(R.dimen.padding_small),
                horizontal = dimensionResource(R.dimen.padding_medium)
            ),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_shadow_elevation_medium)
        )
    ) {
        Column {
            Text(
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_medium)
                ),
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_medium)
                ),
                text = desc,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun DateRangeButton(title: String, onClick: () -> Unit) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.button_elevation)
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onClick.invoke()
            }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Выбор даты",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun BudgetAllocationInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    keyboardType: KeyboardType = KeyboardType.Number,
    enabled: Boolean = true,
    remainedText: String = "",
) {
    // Определяем стиль текста
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        textAlign = TextAlign.Center
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            textStyle = textStyle,
            enabled = enabled,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Осталось: $remainedText%",
            style = textStyle,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalGraphicsApi::class)
@Composable
fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit
) {

    val items = listOf(
        stringResource(R.string.day),
        stringResource(R.string.week),
        stringResource(R.string.month),
        stringResource(R.string.year)
    )

    val backgroundColor = MaterialTheme.colorScheme.surface
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onSurface

    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(surfaceColor)
            .padding(2.dp)
    ) {



        if (items.isNotEmpty()) {

            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(2.dp, RoundedCornerShape(32.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
            )



            Row(modifier = Modifier
                .fillMaxWidth()


                .drawWithContent {

                    // This is for setting black tex while drawing on white background
                    val padding = 8.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 4 - padding * 2, size.height - padding * 2),
                        color = textColor,
                        cornerRadius = CornerRadius(x = 32.dp.toPx(), y = 32.dp.toPx()),
                    )
                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 4, size.height),
                            color = backgroundColor,
                            cornerRadius = CornerRadius(x = 32.dp.toPx(), y = 32.dp.toPx()),
                            blendMode = androidx.compose.ui.graphics.BlendMode.SrcOut
                        )
                    }

                }
            ) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(index)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}


fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}





//
//@Preview
//@Composable
//private fun Preview() {
//    TfinanceTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            GoalCard(
//                name = "Dodge Challenger",
//                description = "wrooom wroom",
//                maxValue = 5555555,
//                onClick = {}
//            )
//        }
//    }
//}


@Preview
@Composable
private fun Preview() {
    TfinanceTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GoalCard(
                name = "Dodge Challenger",
                description = "wrooom wroom",
                amount = 5555555.0,
                onClick = {}
            )
        }
    }
}
