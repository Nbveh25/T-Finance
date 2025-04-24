package ru.practice.t_finance.domain.validator

import javax.inject.Inject

class PhoneNumberValidator @Inject constructor() {

    fun isValid(phoneNumber: String): Boolean {
        return PHONE_REGEX.matches(phoneNumber)
    }

    fun normalizePhoneNumber(phoneNumber: String): String {
        val digits = phoneNumber.replace(Regex("[^0-9]"), "")
        return if (digits.startsWith("8")) "+7${digits.drop(1)}" else digits
    }

    companion object {
        private val PHONE_REGEX = Regex("""^(\+7|8)[\s\-]?\(?9\d{2}\)?[\s\-]?\d{3}[\s\-]?\d{2}[\s\-]?\d{2}${'$'}""")
    }
}