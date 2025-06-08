package ru.practice.t_finance.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practice.t_finance.BuildConfig
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.handler.NetworkResponseAdapterFactory
import ru.practice.t_finance.data.remote.interceptor.TokenInterceptor
import ru.practice.t_finance.data.remote.token.TokenService
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = BuildConfig.BASE_URL

    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @javax.inject.Singleton
    fun provideTokenService(
        apiService: ApiService,
        @ApplicationContext context: Context,
    ): TokenService {
        return TokenService(
            apiService,
            context
        )
    }

    @Provides
    @javax.inject.Singleton
    fun provideTokenInterceptor(
        tokenServiceProvider: Provider<TokenService>
    ): TokenInterceptor {
        return TokenInterceptor(tokenServiceProvider)
    }
}