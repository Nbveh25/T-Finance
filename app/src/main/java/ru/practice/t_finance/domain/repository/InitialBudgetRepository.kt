package ru.practice.t_finance.domain.repository

interface InitialBudgetRepository {
    suspend fun sendInitialBudget(data: String, day: String) : Result<Unit>
}