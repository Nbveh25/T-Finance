package ru.practice.t_finance.domain.usecases

import ru.practice.t_finance.domain.repository.CategoryRepository
import javax.inject.Inject


class CategoryUseCase @Inject constructor(
    val repository: CategoryRepository
) {

}