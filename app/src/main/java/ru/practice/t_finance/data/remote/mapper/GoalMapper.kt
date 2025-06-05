package ru.practice.t_finance.data.remote.mapper


import android.os.Build
import androidx.annotation.RequiresApi
import ru.practice.t_finance.data.remote.request.EditGoalRequest
import ru.practice.t_finance.data.remote.request.GoalRequest
import ru.practice.t_finance.data.remote.response.GoalResponse
import ru.practice.t_finance.domain.model.GetGoalModel
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.domain.model.EditGoalModel
import ru.practice.t_finance.domain.util.DateFormatter

object GoalMapper {

    fun toCreateGoalModel(goalResponse: GoalResponse) = CreateGoalModel(
        name = goalResponse.name,
        term = goalResponse.term,
        amount = goalResponse.amount,
        accumulatedAmount = goalResponse.accumulatedAmount,
        description = goalResponse.description
    )

    fun toGetGoalModel(goalResponse: GoalResponse) = GetGoalModel(
        id = goalResponse.id,
        name = goalResponse.name,
        term = goalResponse.term,
        amount = goalResponse.amount,
        accumulatedAmount = goalResponse.accumulatedAmount,
        description = goalResponse.description
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun toRequest(createGoalModel: CreateGoalModel) = GoalRequest(
        name = createGoalModel.name,
        term = DateFormatter.format(createGoalModel.term),
        amount = createGoalModel.amount,
        accumulatedAmount = createGoalModel.accumulatedAmount,
        description = createGoalModel.description
    )

    fun toRequest(getGoalModel: GetGoalModel) = EditGoalRequest(
        id = getGoalModel.id,
        name = getGoalModel.name,
        term = getGoalModel.term,
        amount = getGoalModel.amount,
        description = getGoalModel.description,
    )

    fun toRequest(getGoalModel: EditGoalModel) = EditGoalRequest(
        id = getGoalModel.id,
        name = getGoalModel.name,
        term = getGoalModel.term,
        amount = getGoalModel.amount,
        description = getGoalModel.description,
    )

}