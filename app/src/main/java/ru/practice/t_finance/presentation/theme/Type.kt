package ru.practice.t_finance.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.practice.t_finance.R


val TinkoffSans = FontFamily(
    Font(R.font.tinkoffsans_bold,FontWeight.Bold)
)

val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular,FontWeight.Normal),
    Font(R.font.roboto_medium,FontWeight.Medium)
)

val CustomTypography = Typography(
    //тайтл
    displayLarge = TextStyle(
        fontFamily = TinkoffSans,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        letterSpacing = 0.5.sp
    ),
    //жирный обычный текст
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 0.5.sp
    ),
    //нежирный обычный текст
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    //медиум текст(использовать по ситуации)
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

val CalendarTypography = Typography(
    headlineSmall = TextStyle( // Заголовок месяца и года
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle( // Числа (дни месяца)
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle( // Названия дней недели (пн, вт...)
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle( // Кнопки "OK" / "Cancel"
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)
