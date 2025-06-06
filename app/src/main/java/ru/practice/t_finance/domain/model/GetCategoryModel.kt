package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class GetCategoryModel(
    val id: Int,
    val name: String,
    val color: Color,
    val iconPath: String?
) {
}