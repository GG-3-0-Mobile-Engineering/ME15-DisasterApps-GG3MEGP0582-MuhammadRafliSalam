package com.raflisalam.disastertracker.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raflisalam.disastertracker.BuildConfig
import com.raflisalam.disastertracker.common.Constant
import com.raflisalam.disastertracker.data.remote.services.DisastersApi
import com.raflisalam.disastertracker.data.remote.services.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("DisasterApi")
    fun provideRetrofitDisasterApi(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_DISASTER)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Singleton
    @Provides
    fun provideDisasterApi(@Named("DisasterApi") retrofit: Retrofit): DisastersApi {
        return retrofit.create(DisastersApi::class.java)
    }

    @Singleton
    @Provides
    @Named("WeatherApi")
    fun provideRetrofitWeatherApi(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_WEATHER)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(@Named("WeatherApi") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

}