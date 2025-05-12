package ru.practice.t_finance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class SmsCodeModel(
    val code: String
)