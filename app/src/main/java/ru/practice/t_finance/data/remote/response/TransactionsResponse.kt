package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionsResponse(
    @SerializedName("transactions")
    val transactions: List<InnerTransactionsResponse>,
    @SerializedName("totalAmount")
    val totalAmount: String
)

data class InnerTransactionsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("category")
    val category: CategoryResponse,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("description")
    val description: String
)