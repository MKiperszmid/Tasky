package com.mk.tasky.authentication.login.di

import com.mk.tasky.authentication.domain.usecase.ValidateEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidatePasswordUseCase
import com.mk.tasky.authentication.domain.utils.EmailMatcher
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
    fun provideLoginUseCases(emailMatcher: EmailMatcher): LoginUseCase {
        return LoginUseCase(
            validEmail = ValidateEmailUseCase(emailMatcher),
            validPassword = ValidatePasswordUseCase()
        )
    }
}
