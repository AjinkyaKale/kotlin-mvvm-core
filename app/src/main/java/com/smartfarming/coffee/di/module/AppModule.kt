package com.smartfarming.coffee.di.module

import android.content.SharedPreferences
import com.smartfarming.coffee.common.SharedPreferenceHelper
import com.smartfarming.coffee.data.remote.service.SmartFarmingService
import com.smartfarming.coffee.di.AppScope
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
    fun smartFarmingService(retrofit: Retrofit): SmartFarmingService = retrofit.create(SmartFarmingService::class.java)
}