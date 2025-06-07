package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class BudgetResponse(
    @SerializedName("amount") val amount: Double,
    @SerializedName("dayOfAdditionOfBudget") val dayOfAdditionOfBudget: Int
)