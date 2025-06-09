package ru.practice.t_finance.data.remote.interceptor

import android.util.Log
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Response
import ru.practice.t_finance.data.remote.token.TokenService
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val tokenServiceProvider: Provider<TokenService>
) : Interceptor {
    
    private val refreshMutex = Mutex()
    @Volatile
    private var isRefreshing = false
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenService = tokenServiceProvider.get()
        val accessToken = tokenService.getAccessToken()

        Log.d("TokenInterceptor", "Request URL: ${chain.request().url}")
        Log.d("TokenInterceptor", "Access token exists: ${accessToken != null}")
        
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        var response = chain.proceed(request)

        // Проверяем, что это не запрос на обновление токена и получен 401
        if (response.code == 401 && !chain.request().url.encodedPath.contains("refresh-token")) {
            val refreshToken = tokenService.getRefreshToken()
            if (refreshToken != null) {
                
                return runBlocking {
                    refreshMutex.withLock {
                        // Проверяем еще раз после получения блокировки
                        if (isRefreshing) {
                            Log.d("TokenInterceptor", "Token refresh already in progress, retrying with current token")
                            val currentToken = tokenService.getAccessToken()
                            if (currentToken != null && currentToken != accessToken) {
                                // Токен был обновлен другим потоком, повторяем запрос
                                val retryRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer $currentToken")
                                    .build()
                                return@withLock chain.proceed(retryRequest)
                            }
                            return@withLock response // Возвращаем исходный ответ
                        }
                        
                        isRefreshing = true
                        Log.d("TokenInterceptor", "Starting token refresh")
                        
                        try {
                            val refreshResponse = tokenService.refreshAccessToken()
                            
                            if (refreshResponse) {
                                val newAccessToken = tokenService.getAccessToken()
                                Log.d("TokenInterceptor", "Token refresh successful")
                                val newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer $newAccessToken")
                                    .build()
                                chain.proceed(newRequest)
                            } else {
                                Log.d("TokenInterceptor", "Token refresh failed, clearing tokens")
                                tokenService.clearTokens()
                                response // Возвращаем исходный 401 ответ
                            }
                        } finally {
                            isRefreshing = false
                        }
                    }
                }
            } else {
                Log.d("TokenInterceptor", "No refresh token available")
                tokenService.clearTokens()
            }
        }

        return response
    }
}