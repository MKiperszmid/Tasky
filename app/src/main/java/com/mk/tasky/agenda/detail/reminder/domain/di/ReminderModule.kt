package com.mk.tasky.agenda.detail.reminder.domain.di

import com.mk.tasky.agenda.detail.reminder.domain.usecase.DeleteReminder
import com.mk.tasky.agenda.detail.reminder.domain.usecase.GetReminder
import com.mk.tasky.agenda.detail.reminder.domain.usecase.ReminderUseCases
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
    fun provideReminderUseCases(
        repository: AgendaRepository
    ): ReminderUseCases {
        return ReminderUseCases(
            getReminder = GetReminder(repository),
            saveReminder = SaveReminder(repository),
            deleteReminder = DeleteReminder(repository)
        )
    }
}
