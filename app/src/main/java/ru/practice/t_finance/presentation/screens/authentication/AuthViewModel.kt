package ru.practice.t_finance.presentation.screens.authentication

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.practice.t_finance.domain.validator.PhoneNumberValidator
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    var phoneNumber: String = ""

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber = phoneNumberValidator.normalizePhoneNumber(phoneNumber)
    }

}