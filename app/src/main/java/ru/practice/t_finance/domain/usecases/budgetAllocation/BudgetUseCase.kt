package ru.practice.t_finance.domain.usecases.budgetAllocation

import ru.practice.t_finance.data.remote.response.CategoryResponse
import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject

class BudgetUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun invoke() : Result<List<CategoryResponse>> {
        return repository.getCategories()
    }
}