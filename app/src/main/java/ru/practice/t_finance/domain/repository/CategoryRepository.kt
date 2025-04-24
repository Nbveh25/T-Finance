package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.Category

interface CategoryRepository {

    suspend fun getCategories() : List<Category>

}