package ru.practice.t_finance.presentation.screens.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.model.Code
import ru.practice.t_finance.domain.model.Name
import ru.practice.t_finance.domain.model.PhoneNumber
import ru.practice.t_finance.domain.usecases.AuthUseCase
import ru.practice.t_finance.domain.validator.PhoneNumberValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    private val _phoneNumberFlow = MutableStateFlow(PhoneNumber(""))
    val phoneNumberFlow = _phoneNumberFlow.asStateFlow()


    

} 