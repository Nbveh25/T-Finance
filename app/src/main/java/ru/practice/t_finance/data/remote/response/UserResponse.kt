package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("budget") val budget: Double,
    @SerializedName("dayOfAdditionOfBudget") val dayOfAdditionOfBudget: Int
)