package com.ajinkya.mvvmdemo

import android.content.Context
import com.ajinkya.mvvmdemo.di.DaggerComponentProvider
import com.ee.core.application.CoreApp


class UsersApplication : CoreApp() {

    companion object {
        lateinit var APPLICATION_CONTEXT: Context
    }

    private val component by lazy { DaggerComponentProvider.appComponent() }

    override fun onCreate() {
        super.onCreate()

        APPLICATION_CONTEXT = this

        initDI(getBaseUrl())

        component.inject(this)
    }

    private fun getBaseUrl(): String {

        return BuildConfig.BASE_URL
    }

}