package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName


data class AddingTransactionRequest(
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("amount") val amount: Double,
    @SerializedName("description") val description: String
)