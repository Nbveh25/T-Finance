package ru.practice.t_finance.data.repository

import androidx.compose.ui.graphics.Color
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getCategories() : List<Category>{
        val list = listOf(
            Category(
                name = "dasda",
                color = Color(0xFF0000FF),
                value = 0.4f
            ),
            Category(
                name = "dasda",
                color = Color(0xFFFF0099),
                value = 0.4f
            )
            ,Category(
                name = "dasda",
                color = Color(0xFFEAA114),
                value = 0.4f
            )
            ,Category(
                name = "dasda",
                color = Color(0xFF00D2C9),
                value = 0.4f
            )
            ,Category(
                name = "dasda",
                color = Color(0xFFFF0000),
                value = 0.4f
            )
        )
        return list
    }

}