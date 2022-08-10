package com.mk.tasky.authentication.login.di

import com.mk.tasky.authentication.login.data.LoginRepositoryImpl
import com.mk.tasky.authentication.login.data.remote.LoginApi
import com.mk.tasky.authentication.login.data.remote.interceptors.ApiKeyInterceptor
import com.mk.tasky.authentication.login.domain.LoginRepository
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
object LoginModule {
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
    fun provideApi(okHttpClient: OkHttpClient): LoginApi {
        return Retrofit.Builder().baseUrl(LoginApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create()
    }

    @Provides
    @Singleton
    fun provideRepository(api: LoginApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }
}
