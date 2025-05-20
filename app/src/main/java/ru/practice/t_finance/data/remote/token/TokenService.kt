package ru.practice.t_finance.data.remote.token

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.exception.ApiError
import ru.practice.t_finance.data.remote.handler.NetworkResponse
import ru.practice.t_finance.data.remote.response.RefreshTokenResponse
import androidx.core.content.edit

@Singleton
class TokenService @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences by lazy {
        createEncryptedSharedPreferences()
    }

    private companion object {
        const val PREFS_NAME = "secure_prefs"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    suspend fun refreshAccessToken(): Boolean {
        val refreshToken = getRefreshToken() ?: return false

        val response: NetworkResponse<RefreshTokenResponse, ApiError> =
            apiService.refreshToken("Bearer $refreshToken")

        return when (response) {
            is NetworkResponse.Success -> {
                setTokens(
                    response.data.accessToken,
                    response.data.refreshToken
                )
                true
            }
            else -> {
                clearTokens()
                false
            }
        }
    }

    fun setTokens(access: String, refresh: String) {
        prefs.edit() {
            putString(KEY_ACCESS_TOKEN, access)
                .putString(KEY_REFRESH_TOKEN, refresh)
        }
        Log.d("TokenService", "Tokens saved successfully")
    }

    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null).also {
            Log.d("TokenService", "Access token: ${if (it != null) "$it" else "null"}")
        }
    }

    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null).also {
            Log.d("TokenService", "Refresh token: ${if (it != null) "$it" else "null"}")
        }
    }

    fun clearTokens() {
        prefs.edit() {
            remove(KEY_ACCESS_TOKEN)
                .remove(KEY_REFRESH_TOKEN)
        }
        Log.d("TokenService", "Tokens cleared")
    }
}