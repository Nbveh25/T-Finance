package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class SendSmsModel(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Int
)
