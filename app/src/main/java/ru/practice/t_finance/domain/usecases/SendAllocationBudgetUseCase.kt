package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.data.remote.response.CategoryResponse
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject


class SendAllocationBudgetUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun getCategories() : Result<List<CategoryResponse>> {
        return repository.getCategories()
    }
}