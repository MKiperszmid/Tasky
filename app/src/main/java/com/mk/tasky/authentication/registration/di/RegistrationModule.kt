package com.mk.tasky.authentication.registration.di

import com.mk.tasky.authentication.domain.usecase.ValidateEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidateFullNameUseCase
import com.mk.tasky.authentication.domain.usecase.ValidatePasswordUseCase
import com.mk.tasky.authentication.domain.utils.EmailMatcher
import com.mk.tasky.authentication.registration.domain.RegistrationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegistrationModule {
    @Provides
    @Singleton
    fun provideRegistrationUseCases(emailMatcher: EmailMatcher): RegistrationUseCase {
        return RegistrationUseCase(
            validEmail = ValidateEmailUseCase(emailMatcher),
            validPassword = ValidatePasswordUseCase(),
            validFullname = ValidateFullNameUseCase()
        )
    }
}
