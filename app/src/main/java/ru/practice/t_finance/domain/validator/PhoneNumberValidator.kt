package ru.practice.t_finance.domain.validator

import javax.inject.Inject

class PhoneNumberValidator @Inject constructor() {

    /**
     * Проверяет, соответствует ли номер телефона формату +7XXXXXXXXXX или 8XXXXXXXXXX
     */
    fun isValid(phoneNumber: String): Boolean {
        val normalized = normalizePhoneNumber(phoneNumber)
        return PHONE_REGEX.matches(normalized)
    }

    /**
     * Нормализует номер телефона к формату +7XXXXXXXXXX
     * - Удаляет все нецифровые символы
     * - Заменяет начальную 8 на +7
     * - Оставляет только 11 цифр (для российских номеров)
     */
    fun normalizePhoneNumber(phoneNumber: String): String {
        val digits = phoneNumber.replace(Regex("[^0-9]"), "")

        return when {
            digits.isEmpty() -> ""
            digits.length == 11 && digits.startsWith("8") -> "+7${digits.substring(1)}"
            digits.length == 10 -> "+7$digits"
            digits.length == 11 && digits.startsWith("7") -> "+$digits"
            digits.length >= 11 -> "+7${digits.takeLast(10)}" // если номер длиннее 11 цифр
            else -> digits // оставляем как есть (неполный номер)
        }
    }

    /**
     * Форматирует номер для отображения в виде +7 (XXX) XXX-XX-XX
     */
    fun formatForDisplay(phoneNumber: String): String {
        val normalized = normalizePhoneNumber(phoneNumber)
        if (normalized.length != 12) return phoneNumber // если номер неполный, возвращаем как есть

        return normalized.replace(
            Regex("""(\+7)(\d{3})(\d{3})(\d{2})(\d{2})"""),
            "+7 ($2) $3-$4-$5"
        )
    }

    companion object {
        // Регулярное выражение для российских номеров в формате +7XXXXXXXXXX
        val PHONE_REGEX = Regex("""^\+7\d{10}${'$'}""")

        // Длина нормализованного номера (12 символов: +7 и 10 цифр)
        const val NORMALIZED_LENGTH = 12
    }
}