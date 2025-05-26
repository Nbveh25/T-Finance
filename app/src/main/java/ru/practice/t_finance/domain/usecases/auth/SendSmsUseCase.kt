package ru.practice.t_finance.domain.usecases.auth

import android.util.Log
import ru.practice.t_finance.data.remote.response.SendSmsResponse
import ru.practice.t_finance.domain.model.CodeModel
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.repository.AuthRepository
import javax.inject.Inject

class SendSmsUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(phoneNumberModel: PhoneNumberModel, codeModel: CodeModel): Result<SendSmsResponse> {
        Log.d("AuthUseCase", "Phone number: ${phoneNumberModel.phoneNumber}")
        return repository.sendCode(phoneNumberModel = phoneNumberModel, codeModel = codeModel)
    }

}