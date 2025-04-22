package ru.practice.t_finance.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.practice.t_finance.data.repository.CategoryRepositoryImpl
import ru.practice.t_finance.domain.repository.CategoryRepository


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideCategoryRepository() : CategoryRepository{
        return CategoryRepositoryImpl()
    }
}