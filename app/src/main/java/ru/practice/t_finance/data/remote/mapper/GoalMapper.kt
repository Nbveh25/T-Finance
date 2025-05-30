package ru.practice.t_finance.data.remote.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import ru.practice.t_finance.data.remote.request.GoalRequest
import ru.practice.t_finance.data.remote.response.GoalResponse
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.domain.util.DateFormatter

object GoalMapper {

    fun toModel(goalResponse: GoalResponse) = GoalModel(
        name = goalResponse.name,
        term = goalResponse.term,
        amount = goalResponse.amount,
        accumulatedAmount = goalResponse.accumulatedAmount,
        description = goalResponse.description
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun toRequest(goalModel: GoalModel) = GoalRequest(
        name = goalModel.name,
        term = DateFormatter.format(goalModel.term),
        amount = goalModel.amount,
        accumulatedAmount = goalModel.accumulatedAmount,
        description = goalModel.description
    )

}

