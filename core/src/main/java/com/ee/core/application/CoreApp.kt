package com.ee.core.application

import android.app.Application
import com.ee.core.di.AppModule
import com.ee.core.di.CoreComponent
import com.ee.core.di.DaggerCoreComponent
import okhttp3.Authenticator

open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
        var isInternet = true
    }

    fun initDI(url: String, tokenAuthenticator: Authenticator? = null) {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this, url, tokenAuthenticator)).build()
    }
}