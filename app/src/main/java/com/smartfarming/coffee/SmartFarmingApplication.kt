package com.smartfarming.coffee

import android.content.Context
import com.ee.core.application.CoreApp
import com.smartfarming.coffee.di.DaggerComponentProvider


class SmartFarmingApplication : CoreApp() {

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