package ru.practice.t_finance.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.practice.t_finance.R
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.model.TransactionModel
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
                elevation = dimensionResource(R.dimen.card_shadow_elevation),
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
        Surface(modifier = Modifier.fillMaxWidth()) {
            TransactionSlot(
                modifier = Modifier,
                transactionModelList = listOf(
                    TransactionModel(
                        iconUrl = "https://avatars.mds.yandex.net/i?id=ce9759b87fb0b2f7276b28e34f0c1ff4e2499d3d-3919804-images-thumbs&n=13",
                        transactionName = "Меган Фокс",
                        categoryName = "Бордель",
                        summa = 55_000_000
                    ),
                    TransactionModel(
                        iconUrl = "https://avatars.mds.yandex.net/i?id=de31ce5f68663b3c96e2c129b26db7f7057723a0-5243188-images-thumbs&n=13",
                        transactionName = "Марго Робби",
                        categoryName = "Бордель",
                        summa = 55_000_000
                    ),
                    TransactionModel(
                        iconUrl = "https://avatars.mds.yandex.net/i?id=d3bc9dcac62f4320d08112a3f53d1209b4a17e6b-13061308-images-thumbs&n=13",
                        transactionName = "Ана де Армас",
                        categoryName = "Бордель",
                        summa = 55_000_000
                    ),
                )
            )
        }
    }
}