package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendCodeRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("code") val code: String
) {
    override fun toString(): String = "SendCodeRequest(phoneNumber='$phoneNumber' code='${code}')" // For Logging
}