package com.mk.tasky.agenda.di

import android.app.Application
import androidx.room.Room
import com.mk.tasky.agenda.data.AgendaRepositoryImpl
import com.mk.tasky.agenda.data.local.AgendaDatabase
import com.mk.tasky.agenda.data.remote.AgendaApi
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideAgendaRepository(
        agendaDatabase: AgendaDatabase,
        agendaApi: AgendaApi
    ): AgendaRepository {
        return AgendaRepositoryImpl(agendaDatabase.dao, agendaApi)
    }
}
