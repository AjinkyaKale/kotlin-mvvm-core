package com.ee.core.di

import android.content.Context
import com.ee.core.networking.AppScheduler
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import javax.inject.Singleton

@Module
class AppModule(val context: Context, val baseUrl: String, val tokenAuthenticator: Authenticator?) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun scheduler(): com.ee.core.networking.Scheduler {
        return AppScheduler()
    }

    @Provides
    @Singleton
    fun baseUrl(): String {
        return baseUrl
    }

    @Provides
    @Singleton
    fun tokenAuthenticator(): Authenticator? {
        return tokenAuthenticator
    }

}