package ru.practice.t_finance.domain.usecases.goal

import ru.practice.t_finance.data.remote.mapper.GoalMapper
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.domain.repository.GoalRepository
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val repository: GoalRepository
) {

    suspend operator fun invoke(): Result<List<GoalModel>> {
        return repository.getGoals().map { goals ->
            goals.map { goal ->
                GoalMapper.toModel(goal)
            }
        }
    }

}