package ru.practice.t_finance.domain.repository

import ru.practice.t_finance.data.remote.response.GoalResponse
import ru.practice.t_finance.domain.model.GetGoalModel
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.domain.model.EditGoalModel

interface GoalRepository {

    suspend fun getGoals(): Result<List<GoalResponse>>

    suspend fun createGoal(createGoalModel: CreateGoalModel): Result<Unit>

    suspend fun editGoal(goalModel: EditGoalModel): Result<Unit>

    suspend fun getGoalById(id: Int): Result<GoalResponse>

}