package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.BudgetModel

interface BudgetRepository {
    suspend fun getBudget(): Result<BudgetModel>
}