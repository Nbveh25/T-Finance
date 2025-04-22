package ru.practice.t_finance.domain.repository

import androidx.compose.ui.graphics.Color
import ru.practice.t_finance.domain.model.Category

object Categories {
    val categories = listOf(
        Category(
            name = "Продукты",
            color = Color(0xFFFF983D),
            value = 0.1f
        ),
        Category(
            name = "Каша",
            color = Color(0xFFFFDD2D),
            value = 0.4f
        ),
        Category(
            name = "Кошка",
            color = Color(0xFF3DBBFF),
            value = 0.3f
        )
    )
}