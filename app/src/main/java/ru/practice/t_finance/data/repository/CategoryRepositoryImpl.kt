package ru.practice.t_finance.data.repository

import androidx.compose.ui.graphics.Color
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getCategories() : List<Category>{
        val list = listOf(
            Category(
                name = "a",
                color = Color(0xFF0000FF),
                value = 0
            ),
            Category(
                name = "aa",
                color = Color(0xFFFF0099),
                value = 0
            )
            ,Category(
                name = "aaa",
                color = Color(0xFFEAA114),
                value = 0
            )
            ,Category(
                name = "aaaaa",
                color = Color(0xFF00D2C9),
                value = 0
            )
            ,Category(
                name = "aaaaa",
                color = Color(0xFFFF0000),
                value = 0
            ),Category(
                name = "aaaaaa",
                color = Color(0xFFEAA114),
                value = 0
            )
            ,Category(
                name = "aaaaaaa",
                color = Color(0xFF00D2C9),
                value = 0
            )
            ,Category(
                name = "bbbbbbbbb",
                color = Color(0xFFFF0000),
                value = 0
            ),Category(
                name = "bbb",
                color = Color(0xFFEAA114),
                value = 0
            )
            ,Category(
                name = "cccc",
                color = Color(0xFF00D2C9),
                value = 0
            )
            ,Category(
                name = "cccccc",
                color = Color(0xFFFF0000),
                value = 0
            ),Category(
                name = "vvvvvvvv",
                color = Color(0xFFEAA114),
                value = 0
            )
            ,Category(
                name = "fff",
                color = Color(0xFF00D2C9),
                value = 0
            )
            ,Category(
                name = "oooo",
                color = Color(0xFFFF0000),
                value = 0
            )
        )
        return list
    }

}