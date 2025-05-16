package ru.practice.t_finance.domain.validator

import javax.inject.Inject

class PhoneNumberValidator @Inject constructor() {

    fun isValid(phoneNumber: String): Boolean {
        return PHONE_REGEX.matches(phoneNumber)
    }

    fun normalizePhoneNumber(phoneNumber: String): String {
        val digits = phoneNumber.replace(Regex("[^0-9]"), "")
        return "+7${digits.takeLast(10)}"
    }

    companion object {
        val PHONE_REGEX = Regex("""^\+7\d{10}${'$'}""")
    }
}