package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.data.remote.response.GoalResponse
import ru.practice.t_finance.domain.model.GoalModel

interface GoalRepository {

    suspend fun getGoals(): Result<List<GoalResponse>>

    suspend fun createGoal(goalModel: GoalModel): Result<Unit>

}