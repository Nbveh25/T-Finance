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
import javax.crypto.AEADBadTagException

@Singleton
class TokenService @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    private var _prefs: SharedPreferences? = null
    private var _useEncryption = true
    
    private val prefs: SharedPreferences
        get() {
            if (_prefs == null) {
                _prefs = createSharedPreferences()
            }
            return _prefs!!
        }

    private companion object {
        const val PREFS_NAME = "secure_prefs"
        const val FALLBACK_PREFS_NAME = "fallback_prefs"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_USE_ENCRYPTION = "use_encryption"
    }

    private fun createSharedPreferences(): SharedPreferences {
        // Сначала пробуем создать EncryptedSharedPreferences
        return try {
            Log.d("TokenService", "Attempting to create EncryptedSharedPreferences")
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val encryptedPrefs = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            
            Log.d("TokenService", "EncryptedSharedPreferences created successfully")
            _useEncryption = true
            
            // Проверяем, можем ли мы читать/писать в них
            testPreferences(encryptedPrefs)
            encryptedPrefs
            
        } catch (e: Exception) {
            Log.e("TokenService", "EncryptedSharedPreferences creation failed: ${e.message}", e)
            // Fallback на обычные SharedPreferences
            createFallbackSharedPreferences()
        }
    }

    private fun testPreferences(prefs: SharedPreferences): Boolean {
        return try {
            val testKey = "test_key"
            val testValue = "test_value"
            
            prefs.edit {
                putString(testKey, testValue)
            }
            
            val retrievedValue = prefs.getString(testKey, null)
            
            prefs.edit {
                remove(testKey)
            }
            
            retrievedValue == testValue
        } catch (e: Exception) {
            Log.e("TokenService", "Preferences test failed: ${e.message}", e)
            false
        }
    }

    private fun createFallbackSharedPreferences(): SharedPreferences {
        Log.w("TokenService", "Using fallback SharedPreferences (unencrypted)")
        _useEncryption = false
        
        // Мигрируем данные из поврежденных encrypted preferences если возможно
        migrateToBrokenEncryptedPreferences()
        
        return context.getSharedPreferences(FALLBACK_PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun migrateToBrokenEncryptedPreferences() {
        try {
            // Пытаемся очистить поврежденные файлы
            clearCorruptedPreferences()
        } catch (e: Exception) {
            Log.e("TokenService", "Failed to migrate from broken encrypted preferences: ${e.message}")
        }
    }

    private fun clearCorruptedPreferences() {
        try {
            val sharedPrefsDir = context.filesDir.parentFile?.let { 
                java.io.File(it, "shared_prefs") 
            }
            if (sharedPrefsDir?.exists() == true) {
                sharedPrefsDir.listFiles()?.forEach { file ->
                    if (file.name.contains(PREFS_NAME)) {
                        val deleted = file.delete()
                        Log.d("TokenService", "Deleted corrupted file: ${file.name}, success: $deleted")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TokenService", "Failed to clear corrupted preferences: ${e.message}", e)
        }
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
                Log.e("TokenService", "Token refresh failed: $response")
                clearTokens()
                false
            }
        }
    }

    fun setTokens(access: String, refresh: String) {
        try {
            prefs.edit {
                putString(KEY_ACCESS_TOKEN, access)
                putString(KEY_REFRESH_TOKEN, refresh)
            }
            Log.d("TokenService", "Tokens saved successfully (encrypted: $_useEncryption)")
        } catch (e: Exception) {
            Log.e("TokenService", "Failed to save tokens: ${e.message}", e)
            // Пробуем пересоздать preferences и повторить
            recreatePreferences()
            try {
                prefs.edit {
                    putString(KEY_ACCESS_TOKEN, access)
                    putString(KEY_REFRESH_TOKEN, refresh)
                }
                Log.d("TokenService", "Tokens saved successfully after recreation")
            } catch (e2: Exception) {
                Log.e("TokenService", "Failed to save tokens even after recreation: ${e2.message}", e2)
            }
        }
    }

    fun getAccessToken(): String? {
        return try {
            prefs.getString(KEY_ACCESS_TOKEN, null).also {
                Log.d("TokenService", "Access token retrieved: ${if (it != null) "present" else "null"} (encrypted: $_useEncryption)")
            }
        } catch (e: AEADBadTagException) {
            Log.e("TokenService", "Failed to decrypt access token, recreating preferences", e)
            recreatePreferences()
            null
        } catch (e: Exception) {
            Log.e("TokenService", "Error getting access token: ${e.message}", e)
            null
        }
    }

    fun getRefreshToken(): String? {
        return try {
            prefs.getString(KEY_REFRESH_TOKEN, null).also {
                Log.d("TokenService", "Refresh token retrieved: ${if (it != null) "present" else "null"} (encrypted: $_useEncryption)")
            }
        } catch (e: AEADBadTagException) {
            Log.e("TokenService", "Failed to decrypt refresh token, recreating preferences", e)
            recreatePreferences()
            null
        } catch (e: Exception) {
            Log.e("TokenService", "Error getting refresh token: ${e.message}", e)
            null
        }
    }

    fun clearTokens() {
        try {
            prefs.edit {
                remove(KEY_ACCESS_TOKEN)
                remove(KEY_REFRESH_TOKEN)
            }
            Log.d("TokenService", "Tokens cleared successfully")
        } catch (e: Exception) {
            Log.e("TokenService", "Failed to clear tokens: ${e.message}", e)
            recreatePreferences()
        }
    }

    private fun recreatePreferences() {
        Log.d("TokenService", "Recreating SharedPreferences")
        _prefs = null
        clearCorruptedPreferences()
        // При следующем обращении к prefs будет создан новый экземпляр
    }

    fun hasValidTokens(): Boolean {
        val hasAccess = getAccessToken() != null
        val hasRefresh = getRefreshToken() != null
        val result = hasAccess && hasRefresh
        Log.d("TokenService", "Token validation: access=$hasAccess, refresh=$hasRefresh, valid=$result")
        return result
    }

    /**
     * Принудительная очистка всех данных приложения связанных с токенами
     * Используется для восстановления после критических ошибок
     */
    fun forceCleanup() {
        Log.w("TokenService", "Performing force cleanup of all token data")
        
        try {
            // Очищаем текущие preferences
            _prefs?.edit()?.clear()?.apply()
            _prefs = null
            
            // Очищаем все файлы preferences
            clearAllPreferencesFiles()
            
            // Пересоздаем с fallback
            _useEncryption = false
            _prefs = context.getSharedPreferences(FALLBACK_PREFS_NAME, Context.MODE_PRIVATE)
            
            Log.w("TokenService", "Force cleanup completed, using fallback preferences")
        } catch (e: Exception) {
            Log.e("TokenService", "Force cleanup failed: ${e.message}", e)
        }
    }

    private fun clearAllPreferencesFiles() {
        try {
            val sharedPrefsDir = context.filesDir.parentFile?.let { 
                java.io.File(it, "shared_prefs") 
            }
            if (sharedPrefsDir?.exists() == true) {
                sharedPrefsDir.listFiles()?.forEach { file ->
                    if (file.name.contains(PREFS_NAME) || file.name.contains(FALLBACK_PREFS_NAME)) {
                        val deleted = file.delete()
                        Log.d("TokenService", "Deleted preferences file: ${file.name}, success: $deleted")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TokenService", "Failed to clear all preferences files: ${e.message}", e)
        }
    }
}