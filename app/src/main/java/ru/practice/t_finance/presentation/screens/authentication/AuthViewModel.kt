package ru.practice.t_finance.presentation.screens.authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.usecases.AuthUseCase
import ru.practice.t_finance.domain.util.ResponseResult
import ru.practice.t_finance.domain.util.flowResponse
import ru.practice.t_finance.domain.validator.PhoneNumberValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    var phoneNumber by mutableStateOf("")
        private set

    var isError by mutableStateOf(false)
        private set

    fun sendCode() = flowResponse {
        val normalized = phoneNumberValidator.normalizePhoneNumber(phoneNumber)

        authUseCase.sendCode(PhoneNumberModel(normalized))
    }

    fun updatePhoneNumber(newValue: String) {
        phoneNumber = formatPhoneNumber(newValue)
        isError = !phoneNumberValidator.isValid(
            phoneNumberValidator.normalizePhoneNumber(phoneNumber)
        )
    }

    private fun formatPhoneNumber(input: String): String {
        val digits = input.replace(Regex("[^0-9]"), "")
        return when {
            digits.length <= 1 -> digits
            digits.startsWith("8") -> "+7 ${digits.drop(1).chunked(3)}"
            else -> buildString {
                append("+7 ")
                digits.drop(1).windowed(3, 3, partialWindows = true).forEachIndexed { i, chunk ->
                    when(i) {
                        0 -> append("($chunk) ")
                        1 -> append("$chunk-")
                        else -> append(chunk)
                    }
                }
            }
        }
    }
}