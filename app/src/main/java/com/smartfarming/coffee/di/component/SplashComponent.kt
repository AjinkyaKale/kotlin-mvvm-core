package com.smartfarming.coffee.di.component


import com.smartfarming.coffee.di.PerActivity
import com.smartfarming.coffee.di.module.SplashModule
import com.smartfarming.coffee.presentation.splash.SplashActivity
import dagger.Component


@PerActivity
@Component(modules = [SplashModule::class])
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)
}