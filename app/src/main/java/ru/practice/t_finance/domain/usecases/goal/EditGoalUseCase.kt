package ru.practice.t_finance.domain.usecases.goal

import android.util.Log
import jakarta.inject.Inject
import ru.practice.t_finance.domain.model.EditGoalModel
import ru.practice.t_finance.domain.repository.GoalRepository

class EditGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(model: EditGoalModel): Result<Unit> {
        Log.d("EditGoalUseCase", model.toString())
        return repository.editGoal(model)
    }
}