package com.smartfarming.coffee.di.module

import com.smartfarming.coffee.di.PerActivity
import com.smartfarming.coffee.presentation.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides

@PerActivity
@Module
class SplashModule {

    private val mSplashTime: Long = 3000

    @Provides
    fun splashViewModelFactory(time: Long): SplashViewModelFactory = SplashViewModelFactory(time)

    @Provides
    @PerActivity
    fun splashTime(): Long {
        return mSplashTime
    }

}