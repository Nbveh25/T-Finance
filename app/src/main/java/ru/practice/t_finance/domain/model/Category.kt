package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Category(
    val id: Int,
    val name: String,
    val color: Color,
    val value: Int = 0,
){

    override fun equals(other: Any?): Boolean {
        if (other !is Category) return false
        return id == other.id
    }

    override fun hashCode(): Int {
    return id.hashCode()
    }
}
