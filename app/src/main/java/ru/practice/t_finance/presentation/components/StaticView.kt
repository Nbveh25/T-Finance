package ru.practice.t_finance.presentation.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.presentation.theme.TfinanceTheme

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    centerText: Boolean = false // Новый параметр для центрирования текста
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
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape)),
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
        visualTransformation = visualTransformation
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
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape)),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                vertical = dimensionResource(R.dimen.padding_medium)
            ),
            textAlign = TextAlign.Center
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
            size = androidx.compose.ui.geometry.Size(outerRadius * 2, outerRadius * 2)
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
fun CategoryTile(category: Category, onClick: () -> Unit, isSelected: Boolean){
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(category.color)
            .clickable { onClick()}
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedCategories(
    selectedCategories: List<Category>,
    onCategoryDeselected: (Category) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
    categories: List<Category>,
    selectedCategories: List<Category>,
    onCategorySelected: (Category) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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


@Preview
@Composable
private fun Preview(){
    TfinanceTheme{
//        BudgetDiagram(Modifier,
//            data = Categories.categories
//            )
//        CategoryTile(category = Category("Продукты",Color(0xFFFF983D)), {}, false)
    }
}