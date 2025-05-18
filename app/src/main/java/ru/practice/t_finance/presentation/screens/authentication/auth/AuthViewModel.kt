package ru.practice.t_finance.presentation.screens.authentication.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.usecases.AuthUseCase
import ru.practice.t_finance.domain.validator.PhoneNumberValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    var phoneNumber by mutableStateOf("")
    var formattedPhoneNumber by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    private val _state = MutableStateFlow<AuthScreenState>(AuthScreenState.Initial)
    internal val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    fun sendCode() {
        val normalized = phoneNumberValidator.normalizePhoneNumber(phoneNumber)
        if (!phoneNumberValidator.isValid(normalized)) {
            errorMessage = "Номер должен быть в формате +7XXXXXXXXXX"
            _state.value = AuthScreenState.Error(errorMessage!!)
            return
        }

        viewModelScope.launch {
            _state.value = AuthScreenState.Loading
            authUseCase(PhoneNumberModel(normalized))
                .onSuccess {
                    _state.value = AuthScreenState.Success
                }
                .onFailure { error ->
                    errorMessage = error.message ?: "Ошибка при отправке кода"
                    _state.value = AuthScreenState.Error(errorMessage!!)
                }
        }
    }

    fun updatePhoneNumber(newValue: String) {
        val digits = newValue.filter { it.isDigit() }.take(11)
        phoneNumber = digits
        formattedPhoneNumber = phoneNumberValidator.formatInput(digits)
        errorMessage = null
    }
}