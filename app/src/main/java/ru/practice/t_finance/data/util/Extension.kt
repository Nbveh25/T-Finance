package ru.practice.t_finance.data.util

import androidx.compose.ui.graphics.Color

fun String.toComposeColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}