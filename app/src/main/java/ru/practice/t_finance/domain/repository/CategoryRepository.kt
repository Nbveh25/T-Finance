package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.data.remote.response.CategoryResponse
import ru.practice.t_finance.domain.model.Category

interface CategoryRepository {

    suspend fun getCategories() : Result<List<CategoryResponse>>


}