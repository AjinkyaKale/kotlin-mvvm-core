package com.smartfarming.coffee.di.component

import com.ee.core.di.CoreComponent
import com.smartfarming.coffee.SmartFarmingApplication
import com.smartfarming.coffee.common.SharedPreferenceHelper
import com.smartfarming.coffee.data.remote.service.SmartFarmingService
import com.smartfarming.coffee.di.AppScope
import com.smartfarming.coffee.di.module.AppModule
import com.smartfarming.coffee.di.module.SplashModule
import com.smartfarming.coffee.presentation.splash.SplashActivity
import dagger.Component

@AppScope
@Component(dependencies = [CoreComponent::class], modules = [AppModule::class])
interface AppComponent {

    fun sharedPreferenceHelper(): SharedPreferenceHelper
    fun smartFarmingService(): SmartFarmingService


    fun inject(application: SmartFarmingApplication)

    fun inject(splashActivity: SplashActivity)

    // sub-components
    fun addSplashComponent(splashModule: SplashModule): SplashComponent
}