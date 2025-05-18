package ru.practice.t_finance.data.remote.response

import com.google.gson.annotations.SerializedName

data class SendSmsResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("expiresIn") val expiresIn: Int

)