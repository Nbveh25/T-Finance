package ru.practice.t_finance.presentation.screens.authentication.auth

import android.util.Log
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
    var errorMessage by mutableStateOf<String?>(null)

    private val _state = MutableStateFlow<AuthScreenState>(AuthScreenState.Initial)
    internal val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    fun sendCode() {
        if (!phoneNumberValidator.isValid(phoneNumber)) {
            errorMessage = "Неверный формат номера телефона"
            _state.value = AuthScreenState.Error(errorMessage.toString())
            Log.d("AuthViewModel", "Client")
        } else {
            viewModelScope.launch {
                _state.value = AuthScreenState.Loading
                errorMessage = null

                authUseCase(PhoneNumberModel(phoneNumber))
                    .onSuccess {
                        _state.value = AuthScreenState.Success
                    }
                    .onFailure { error ->
                        Log.d("AuthViewModel", "${error.message}")
                        errorMessage = error.message ?: "Произошла ошибка при отправке кода"
                        _state.value = AuthScreenState.Error(error.message ?: "Неизвестная ошибка")
                    }
            }
        }
    }

    fun updatePhoneNumber(newValue: String) {
        // Удаляем все нецифровые символы и ограничиваем длину
        val digitsOnly = newValue.filter { it.isDigit() }.take(11)
        phoneNumber = digitsOnly
        errorMessage = null
    }

}