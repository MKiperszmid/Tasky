package com.mk.tasky.authentication.di

import com.mk.tasky.authentication.domain.usecase.FormValidatorUseCase
import com.mk.tasky.authentication.domain.usecase.ValidateFullNameUseCase
import com.mk.tasky.authentication.domain.usecase.ValidatePasswordUseCase
import com.mk.tasky.core.domain.usecase.ValidateEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideFormValidator(validateEmail: ValidateEmailUseCase): FormValidatorUseCase {
        return FormValidatorUseCase(
            validateEmail,
            ValidatePasswordUseCase(),
            ValidateFullNameUseCase()
        )
    }
}
