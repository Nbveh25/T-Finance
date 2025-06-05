package ru.practice.t_finance.domain.usecases.goal

import ru.practice.t_finance.data.remote.mapper.GoalMapper
import ru.practice.t_finance.domain.model.GetGoalModel
import ru.practice.t_finance.domain.repository.GoalRepository
import javax.inject.Inject

class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalRepository
) {

    suspend operator fun invoke(id: Int) : Result<GetGoalModel> {
        return repository.getGoalById(id).map { goal ->
            GoalMapper.toGetGoalModel(goal)
        }
    }

}