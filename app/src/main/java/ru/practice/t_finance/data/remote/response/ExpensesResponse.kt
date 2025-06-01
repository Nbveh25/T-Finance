package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName
import ru.practice.t_finance.domain.model.Category

data class ExpensesResponse(
    @SerializedName("sumOfAllTransactions")
    val amount: Int,
    @SerializedName("categories")
    val categories: List<CategoryExpensesResponse>
)