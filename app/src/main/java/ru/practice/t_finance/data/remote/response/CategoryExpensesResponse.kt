package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryExpensesResponse(
    @SerializedName("category")
    val category: CategoryInnerResponse,

    @SerializedName("sum")
    val sum: Double,

    @SerializedName("percentageOfAllTransactions")
    val percentage: Double
)

data class CategoryInnerResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("color")
    val color: String // Например: "#FFFFFF"
)