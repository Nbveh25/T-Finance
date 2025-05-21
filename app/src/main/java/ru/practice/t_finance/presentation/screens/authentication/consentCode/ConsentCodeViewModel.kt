package ru.practice.t_finance.presentation.screens.authentication.consentCode

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.usecases.AuthUseCase
import javax.inject.Inject

@HiltViewModel
class ConsentCodeViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var code by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    val phoneNumber: String = ("+" + (savedStateHandle["phoneNumber"] ?: ""))
        .replace("[\\s-()]".toRegex(), "")

    private val _state = MutableStateFlow<ConsentCodeState>(ConsentCodeState.Initial)
    internal val state: StateFlow<ConsentCodeState> = _state.asStateFlow()

    fun updateCode(newValue: String) {
        code = newValue.take(4)
        errorMessage = null
    }

    fun sendCode() {
        if (code.length != 4) {
            errorMessage = "Введите 4-значный код"
            _state.value = ConsentCodeState.Error(errorMessage.toString())
        } else {
            viewModelScope.launch {
                _state.value = ConsentCodeState.Loading
                Log.d("ConsentCodeViewModel", "Phone number: $phoneNumber")
                authUseCase.invoke(
                    phoneNumberModel = PhoneNumberModel(phoneNumber),
                    codeModel = CodeModel(code),
                ).onSuccess {
                    _state.value = ConsentCodeState.Success
                    Log.d("ConsentCodeViewModel", "Success: $it")
                }.onFailure { error ->
                    errorMessage = error.message ?: "Ошибка при проверке кода"
                    _state.value = ConsentCodeState.Error(errorMessage ?: "Ошибка")
                    Log.d("ConsentCodeViewModel", "Error: $error")
                }
            }
        }
    }

}