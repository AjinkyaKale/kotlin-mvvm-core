package com.ajinkya.mvvmdemo.di.module

import android.content.SharedPreferences
import com.ajinkya.mvvmdemo.common.SharedPreferenceHelper
import com.ajinkya.mvvmdemo.data.remote.service.UsersService
import com.ajinkya.mvvmdemo.di.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
@AppScope
class AppModule {

    @AppScope
    @Provides
    fun provideSharedPref(sharedPreferences: SharedPreferences): SharedPreferenceHelper =
        SharedPreferenceHelper(sharedPreferences)

    @AppScope
    @Provides
    fun smartFarmingService(retrofit: Retrofit): UsersService =
        retrofit.create(UsersService::class.java)
}