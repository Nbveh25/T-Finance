package ru.practice.t_finance.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.token.TokenService
import ru.practice.t_finance.data.repository.AuthRepositoryImpl
import ru.practice.t_finance.data.repository.CategoryRepositoryImpl
import ru.practice.t_finance.data.repository.GoalRepositoryImpl
import ru.practice.t_finance.domain.repository.AuthRepository
import ru.practice.t_finance.domain.repository.CategoryRepository
import ru.practice.t_finance.domain.repository.GoalRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(): CategoryRepository {
        return CategoryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        tokenService: TokenService
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, tokenService)
    }

    @Provides
    @Singleton
    fun provideGoalRepository(
        apiService: ApiService,
    ): GoalRepository {
        return GoalRepositoryImpl(apiService)
    }
}