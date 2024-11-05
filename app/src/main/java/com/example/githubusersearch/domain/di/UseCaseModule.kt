package com.example.githubusersearch.domain.di

import com.example.githubusersearch.data.repository.RepositoryImpl
import com.example.githubusersearch.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserUseCase(repositoryImpl: RepositoryImpl) = GetUserUseCase(repositoryImpl)
}