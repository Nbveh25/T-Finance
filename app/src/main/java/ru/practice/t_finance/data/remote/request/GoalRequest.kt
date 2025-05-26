package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class GoalRequest(
    @SerializedName("name") val name: String,
    @SerializedName("term") val term: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("accumulatedAmount") val accumulatedAmount: Double,
    @SerializedName("description") val description: String
) {
    override fun toString(): String {
        return "${term}"
    }
}