package com.mk.tasky.authentication.login.di

import com.mk.tasky.authentication.domain.usecase.ValidEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidPasswordUseCase
import com.mk.tasky.authentication.login.domain.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    @Singleton
    fun provideLoginUseCases(): LoginUseCase {
        return LoginUseCase(
            validEmail = ValidEmailUseCase(),
            validPassword = ValidPasswordUseCase()
        )
    }
}
