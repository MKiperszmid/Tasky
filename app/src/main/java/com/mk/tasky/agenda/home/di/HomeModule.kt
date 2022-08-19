package com.mk.tasky.agenda.home.di

import com.mk.tasky.agenda.home.domain.usecase.FormatNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideFormatName(): FormatNameUseCase {
        return FormatNameUseCase()
    }
}
