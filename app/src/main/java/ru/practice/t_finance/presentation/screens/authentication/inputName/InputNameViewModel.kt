package ru.practice.t_finance.presentation.screens.authentication.inputName

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
import ru.practice.t_finance.domain.model.FirstNameModel
import ru.practice.t_finance.domain.usecases.auth.SendNameUseCase
import javax.inject.Inject

@HiltViewModel
class InputNameViewModel @Inject constructor(
    val sendNameUseCase: SendNameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<InputNameScreenState>(InputNameScreenState.Initial)
    internal val state: StateFlow<InputNameScreenState> = _state.asStateFlow()

    var name by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun updateName(newValue: String) {
        name = newValue
    }

    fun sendName() {
        if (name.isBlank()) {
            errorMessage = "Введите имя"
        } else {
            viewModelScope.launch {
                _state.value = InputNameScreenState.Loading
                Log.d("InputNameViewModel", "First name: $name")
                sendNameUseCase.invoke(
                    firstNameModel = FirstNameModel(name)
                ).onSuccess {
                    _state.value = InputNameScreenState.Success
                    errorMessage = null
                    Log.d("InputNameViewModel", "Success: ${it}")
                }.onFailure { error ->
                    _state.value = InputNameScreenState.Error(error.message ?: "Ошибка")
                    errorMessage = error.message ?: "Ошибка"
                    Log.d("InputNameViewModel", "Error: ${error.message}")
                }
            }
        }
    }
}