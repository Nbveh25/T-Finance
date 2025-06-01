package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Category(
    val name: String,
    val color: Color,
    val value: Int = 0,
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Category) return false
        return name == other.name
    }

    override fun hashCode(): Int {
    return name.hashCode()
    }
}
