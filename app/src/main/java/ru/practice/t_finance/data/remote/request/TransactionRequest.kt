package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName


data class TransactionRequest(
    @SerializedName("amount") val amount: Double,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("description") val description: String
)