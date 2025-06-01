package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.domain.model.ExpensesGraph

interface ExpensesRepository {
    suspend fun getExpenses(startDate: String, endDate: String) : Result<ExpensesGraph>
}