package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendSmsRequest(
    @SerializedName("phoneNumber") val phoneNumber: String
) {
    override fun toString(): String = "SendSmsRequest(phoneNumber='$phoneNumber')" // For Logging
}
