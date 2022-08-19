package com.mk.tasky.authentication.di

import com.mk.tasky.authentication.data.utils.EmailMatcherImpl
import com.mk.tasky.authentication.domain.usecase.FormValidatorUseCase
import com.mk.tasky.authentication.domain.usecase.ValidateEmailUseCase
import com.mk.tasky.authentication.domain.usecase.ValidateFullNameUseCase
import com.mk.tasky.authentication.domain.usecase.ValidatePasswordUseCase
import com.mk.tasky.authentication.domain.utils.EmailMatcher
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
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }

    @Provides
    @Singleton
    fun provideFormValidator(emailMatcher: EmailMatcher): FormValidatorUseCase {
        return FormValidatorUseCase(
            ValidateEmailUseCase(emailMatcher),
            ValidatePasswordUseCase(),
            ValidateFullNameUseCase()
        )
    }
}
