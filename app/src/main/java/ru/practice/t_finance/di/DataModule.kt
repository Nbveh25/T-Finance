package ru.practice.t_finance.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.practice.t_finance.data.remote.api.ApiService
import ru.practice.t_finance.data.remote.token.TokenService
import ru.practice.t_finance.data.repository.AuthRepositoryImpl
import ru.practice.t_finance.data.repository.BudgetRepositoryImpl
import ru.practice.t_finance.data.repository.CategoryRepositoryImpl
import ru.practice.t_finance.data.repository.ExpensesRepositoryImpl
import ru.practice.t_finance.data.repository.GoalRepositoryImpl
import ru.practice.t_finance.data.repository.InitialBudgetRepositoryImpl
import ru.practice.t_finance.data.repository.TransactionRepositoryImpl
import ru.practice.t_finance.domain.repository.AuthRepository
import ru.practice.t_finance.domain.repository.BudgetRepository
import ru.practice.t_finance.domain.repository.CategoryRepository
import ru.practice.t_finance.domain.repository.ExpensesRepository
import ru.practice.t_finance.domain.repository.GoalRepository
import ru.practice.t_finance.domain.repository.InitialBudgetRepository
import ru.practice.t_finance.domain.repository.TransactionRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideBudgetRepository(apiService: ApiService): BudgetRepository {
        return BudgetRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(apiService: ApiService): TransactionRepository {
        return TransactionRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideExpensesRepositoryImpl(apiService: ApiService) : ExpensesRepository {
        return ExpensesRepositoryImpl(apiService)
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

    @Provides
    @Singleton
    fun provideInitialRepository(
        apiService: ApiService
    ) : InitialBudgetRepository {
        return InitialBudgetRepositoryImpl(apiService)
    }
}