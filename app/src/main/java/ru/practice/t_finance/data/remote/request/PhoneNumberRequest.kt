package ru.practice.t_finance.data.remote.request

import com.google.gson.annotations.SerializedName

data class PhoneNumberRequest(
    @SerializedName("phoneNumber") val phoneNumber: String
)
