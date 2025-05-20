package ru.practice.t_finance.data.remote.interceptor

import android.util.Log
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.practice.t_finance.data.remote.token.TokenService
import javax.inject.Provider

class TokenInterceptor @Inject constructor(
    private val tokenServiceProvider: Provider<TokenService>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenService = tokenServiceProvider.get()
        val accessToken = tokenService.getAccessToken()

        Log.d("TokenInterceptor", "accessToken: $accessToken")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        var response = chain.proceed(request)

        if (response.code == 401) {
            val refreshToken = tokenService.getRefreshToken()
            if (refreshToken != null) {

                // runBlocking плохая практика?
                val refreshResponse = runBlocking {
                    tokenService.refreshAccessToken()
                }

                if (refreshResponse) {
                    val newAccessToken = tokenService.getAccessToken()
                    Log.d("TokenInterceptor", "newAccessToken: $newAccessToken")
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                    response = chain.proceed(newRequest)
                } else {
                    tokenService.clearTokens()
                }
            }
        }

        return response
    }
}