package ru.practice.t_finance.domain.usecases.goal

import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.domain.repository.GoalRepository
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {

    suspend operator fun invoke(model: CreateGoalModel): Result<Unit> {
        return repository.createGoal(model)
    }

}