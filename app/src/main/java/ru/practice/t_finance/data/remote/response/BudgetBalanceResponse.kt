package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class BudgetBalanceResponse(
    @SerializedName("balance") val balance: Double
)