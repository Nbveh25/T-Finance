package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendInitialBudgetRequest(
    @SerializedName("amount")
    val amount : String,
    @SerializedName("dayOfAdditionOfBudget")
    val day : String
)
