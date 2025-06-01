package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class GoalResponse (
    @SerializedName("id") val id: Int,
    @SerializedName ("name") val name: String,
    @SerializedName ("term") val term: String,
    @SerializedName ("amount") val amount: Double,
    @SerializedName ("accumulatedAmount") val accumulatedAmount: Double,
    @SerializedName("description") val description: String
)