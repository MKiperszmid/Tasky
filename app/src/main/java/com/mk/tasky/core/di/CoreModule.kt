package com.mk.tasky.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mk.tasky.core.data.preferences.DefaultPreferences
import com.mk.tasky.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }
}
