package com.mk.tasky.agenda.di

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.mk.tasky.agenda.data.AgendaRepositoryImpl
import com.mk.tasky.agenda.data.alarm.AlarmRegisterImpl
import com.mk.tasky.agenda.data.local.AgendaDatabase
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.data.remote.uploader.EventUploaderImpl
import com.mk.tasky.agenda.data.uri.PhotoByteConverterImpl
import com.mk.tasky.agenda.data.uri.PhotoExtensionParserImpl
import com.mk.tasky.agenda.domain.alarm.AlarmRegister
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.domain.uploader.EventUploader
import com.mk.tasky.agenda.domain.uri.PhotoByteConverter
import com.mk.tasky.agenda.domain.uri.PhotoExtensionParser
import com.mk.tasky.agenda.domain.usecase.event.*
import com.mk.tasky.agenda.domain.usecase.home.FormatNameUseCase
import com.mk.tasky.agenda.domain.usecase.home.HomeUseCases
import com.mk.tasky.agenda.domain.usecase.reminder.DeleteReminder
import com.mk.tasky.agenda.domain.usecase.reminder.GetReminder
import com.mk.tasky.agenda.domain.usecase.reminder.ReminderUseCases
import com.mk.tasky.agenda.domain.usecase.reminder.SaveReminder
import com.mk.tasky.agenda.domain.usecase.task.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AgendaModule {
    @Provides
    @Singleton
    fun provideAgendaDatabase(application: Application): AgendaDatabase {
        return Room.databaseBuilder(
            application,
            AgendaDatabase::class.java,
            "agenda_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAgendaApi(okHttpClient: OkHttpClient): AgendaApi {
        return Retrofit.Builder().baseUrl(AgendaApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create()
    }

    @Provides
    @Singleton
    fun provideAlarmRegister(application: Application): AlarmRegister {
        return AlarmRegisterImpl(application)
    }

    @Provides
    @Singleton
    fun provideAgendaRepository(
        agendaDatabase: AgendaDatabase,
        agendaApi: AgendaApi,
        alarmRegister: AlarmRegister
    ): AgendaRepository {
        return AgendaRepositoryImpl(
            agendaDatabase.dao,
            agendaApi,
            alarmRegister
        )
    }

    @Provides
    @Singleton
    fun provideDeleteReminderUseCase(
        repository: AgendaRepository
    ): DeleteReminder {
        return DeleteReminder(repository)
    }

    @Provides
    @Singleton
    fun provideFormatName(): FormatNameUseCase {
        return FormatNameUseCase()
    }

    @Provides
    @Singleton
    fun provideReminderUseCases(
        repository: AgendaRepository,
        deleteReminder: DeleteReminder
    ): ReminderUseCases {
        return ReminderUseCases(
            getReminder = GetReminder(repository),
            saveReminder = SaveReminder(repository),
            deleteReminder = deleteReminder
        )
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(
        repository: AgendaRepository
    ): DeleteTask {
        return DeleteTask(repository)
    }

    @Provides
    @Singleton
    fun provideChangeStatusTaskUseCase(
        repository: AgendaRepository
    ): ChangeStatusTask {
        return ChangeStatusTask(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteEventUseCase(
        repository: AgendaRepository
    ): DeleteEvent {
        return DeleteEvent(repository)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        deleteTask: DeleteTask,
        deleteReminder: DeleteReminder,
        changeStatusTask: ChangeStatusTask,
        deleteEvent: DeleteEvent
    ): HomeUseCases {
        return HomeUseCases(
            deleteReminder = deleteReminder,
            deleteTask = deleteTask,
            changeStatusTask = changeStatusTask,
            deleteEvent = deleteEvent
        )
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(
        repository: AgendaRepository,
        deleteTask: DeleteTask
    ): TaskUseCases {
        return TaskUseCases(
            getTask = GetTask(repository),
            saveTask = SaveTask(repository),
            deleteTask = deleteTask
        )
    }

    @Provides
    @Singleton // TODO: Update with all the use cases
    fun provideEventUseCases(
        repository: AgendaRepository,
        eventUploader: EventUploader,
        deleteEvent: DeleteEvent
    ): EventUseCases {
        return EventUseCases(
            SaveEvent(repository, eventUploader),
            GetAttendee(repository),
            GetEvent(repository),
            deleteEvent
        )
    }

    @Provides
    @Singleton
    fun providePhotoByteConverter(
        application: Application,
        dispatcher: CoroutineDispatcher
    ): PhotoByteConverter {
        return PhotoByteConverterImpl(application, dispatcher)
    }

    @Provides
    @Singleton
    fun providePhotoExtensionParser(
        application: Application
    ): PhotoExtensionParser {
        return PhotoExtensionParserImpl(application)
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        application: Application
    ): WorkManager {
        return WorkManager.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideEventUploader(workManager: WorkManager): EventUploader {
        return EventUploaderImpl(workManager)
    }
}
