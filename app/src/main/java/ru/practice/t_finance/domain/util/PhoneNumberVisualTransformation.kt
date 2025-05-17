package ru.practice.t_finance.domain.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Формат: +7 (XXX) XXX-XX-XX
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        var out = "+7 "
        for (i in trimmed.indices) {
            when (i) {
                0 -> out += "("
                3 -> out += ") "
                6 -> out += "-"
                8 -> out += "-"
                else -> {}
            }
            out += trimmed[i]
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 3
                if (offset <= 3) return offset + 4
                if (offset <= 6) return offset + 6
                if (offset <= 8) return offset + 7
                if (offset <= 10) return offset + 8
                return 18
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return 0
                if (offset <= 7) return offset - 4
                if (offset <= 13) return offset - 6
                if (offset <= 16) return offset - 7
                if (offset <= 18) return offset - 8
                return 11
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}