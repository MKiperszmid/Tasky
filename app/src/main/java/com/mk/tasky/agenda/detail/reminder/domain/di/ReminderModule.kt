package com.mk.tasky.agenda.detail.reminder.domain.di

import com.mk.tasky.agenda.detail.reminder.domain.usecase.SaveReminder
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReminderModule {
    @Provides
    @Singleton
    fun provideSaveReminder(
        repository: AgendaRepository
    ): SaveReminder {
        return SaveReminder(repository)
    }
}
