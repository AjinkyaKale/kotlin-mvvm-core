package com.smartfarming.coffee.di.component

import com.ee.core.di.CoreComponent
import com.smartfarming.coffee.SmartFarmingApplication
import com.smartfarming.coffee.common.SmartFarmingSharedPreferences
import com.smartfarming.coffee.di.AppScope
import com.smartfarming.coffee.di.module.AppModule
import dagger.Component

@AppScope
@Component(dependencies = [CoreComponent::class], modules = [AppModule::class])
interface AppComponent {

    fun smartFarmingSharedPreference(): SmartFarmingSharedPreferences

    fun inject(application: SmartFarmingApplication)

}