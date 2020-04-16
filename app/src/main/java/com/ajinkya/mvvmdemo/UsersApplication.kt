package com.ajinkya.mvvmdemo

import com.ajinkya.mvvmdemo.di.DaggerComponentProvider
import com.ee.core.application.CoreApp


class UsersApplication : CoreApp() {

    private val component by lazy { DaggerComponentProvider.appComponent() }

    override fun onCreate() {

        super.onCreate()

        initDI(getBaseUrl())

        component.inject(this)
    }

    private fun getBaseUrl(): String {

        return BuildConfig.BASE_URL
    }

}