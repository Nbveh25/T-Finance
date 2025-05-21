package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendNameRequest(
    @SerializedName("firstName") val firstName: String
) {
    override fun toString(): String = "SendNameRequest(firstName=${firstName})" // For logging
}