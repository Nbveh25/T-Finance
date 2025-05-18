package ru.practice.t_finance.domain.validator

import javax.inject.Inject

class PhoneNumberValidator @Inject constructor() {

    /**
     * Проверяет, соответствует ли номер телефона формату +7XXXXXXXXXX
     */
    fun isValid(phoneNumber: String): Boolean {
        val normalized = normalizePhoneNumber(phoneNumber)
        return PHONE_REGEX.matches(normalized)
    }

    /**
     * Нормализует номер телефона к строгому формату +7XXXXXXXXXX
     */
    fun normalizePhoneNumber(phoneNumber: String): String {
        // Удаляем все нецифровые символы
        val digits = phoneNumber.replace(Regex("[^0-9]"), "")

        return when {
            digits.isEmpty() -> ""
            digits.startsWith("8") && digits.length == 11 -> "+7${digits.substring(1)}"
            digits.startsWith("7") && digits.length == 11 -> "+$digits"
            digits.length == 10 -> "+7$digits"
            digits.length > 11 -> "+7${digits.takeLast(10)}" // берем последние 10 цифр
            else -> digits
        }
    }

    /**
     * Форматирует ввод пользователя в процессе набора
     */
    fun formatInput(phoneNumber: String): String {
        val digits = phoneNumber.filter { it.isDigit() }.take(11)
        return when {
            digits.isEmpty() -> ""
            digits.startsWith("8") -> "+7" + digits.drop(1)
            digits.startsWith("7") -> "+" + digits
            else -> digits
        }
    }

    companion object {
        val PHONE_REGEX = Regex("^\\+7[0-9]{10}$")
        const val NORMALIZED_LENGTH = 12 // +7 + 10 цифр
    }
}