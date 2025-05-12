package ru.practice.t_finance.data.repository

import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.mapper.AuthMapper
import ru.practice.t_finance.domain.model.PhoneNumberModel
import ru.practice.t_finance.domain.repository.AuthRepository
import ru.practice.t_finance.domain.util.safeApiCall
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun sendCode(phoneNumberModel: PhoneNumberModel) = safeApiCall {
        apiService.sendCode(AuthMapper.toRequest(phoneNumberModel))
    }

}