package com.mk.tasky.authentication.di

import com.mk.tasky.authentication.data.AuthenticationRepositoryImpl
import com.mk.tasky.authentication.data.remote.AuthenticationApi
import com.mk.tasky.authentication.data.remote.interceptors.ApiKeyInterceptor
import com.mk.tasky.authentication.data.utils.EmailMatcherImpl
import com.mk.tasky.authentication.domain.AuthenticationRepository
import com.mk.tasky.authentication.domain.utils.EmailMatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Singleton
    @Provides
    fun provideOKhttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).addInterceptor(ApiKeyInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): AuthenticationApi {
        return Retrofit.Builder().baseUrl(AuthenticationApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create()
    }

    @Provides
    @Singleton
    fun provideRepository(api: AuthenticationApi): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }
}
