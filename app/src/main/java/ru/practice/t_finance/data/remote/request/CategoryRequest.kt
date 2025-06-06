package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class CategoryRequest(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("percent")
    val percent: String,
    @SerializedName("notificationLimit")
    val notificationLimit : String = "90"
)