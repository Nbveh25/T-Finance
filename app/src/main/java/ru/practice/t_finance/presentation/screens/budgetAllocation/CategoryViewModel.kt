package ru.practice.t_finance.presentation.screens.budgetAllocation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.practice.t_finance.domain.model.Category
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(): ViewModel(){

    fun getCategories() : List<Category> {
        val list = listOf(
            Category(
              name = "dasda",
                color = Color(0xFF333335),
            )
        )
        return list
    }
}