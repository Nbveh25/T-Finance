package ru.practice.t_finance.domain.usecases.budgetAllocation

import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject

class SendCategoriesToNetworkUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun invoke(data: List<Category>) : Result<Unit>{
        return categoryRepository.sendCategories(data)
    }
}