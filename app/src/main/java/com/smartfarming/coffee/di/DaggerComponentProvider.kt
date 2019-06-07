package com.smartfarming.coffee.di

import com.ee.core.application.CoreApp
import com.smartfarming.coffee.di.component.AppComponent
import com.smartfarming.coffee.di.component.DaggerAppComponent
import com.smartfarming.coffee.di.component.DaggerSplashComponent
import com.smartfarming.coffee.di.component.SplashComponent
import javax.inject.Singleton

@Singleton
object DaggerComponentProvider {

    private var mAppComponent: AppComponent? = null
    private var mSplashComponent: SplashComponent? = null

    fun appComponent(): AppComponent {
        if (mAppComponent == null)
            mAppComponent = DaggerAppComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return mAppComponent as AppComponent
    }

    fun splashComponent(): SplashComponent {
        if (mSplashComponent == null)
            mSplashComponent = DaggerSplashComponent.builder().build()
        return mSplashComponent as SplashComponent
    }

}