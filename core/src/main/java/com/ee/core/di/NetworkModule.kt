package com.ee.core.di

import android.content.Context
import com.ee.core.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        jacksonConverterFactory: JacksonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(jacksonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenAuthenticator: Authenticator?
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor)
        }
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)

            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Connection", "close")
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()
                val response = chain.proceed(request)
                if (response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                    throw NoContentException("No content")
                }
                return response
            }
        })

        if (tokenAuthenticator != null) {
            httpClient.authenticator(tokenAuthenticator)
        }

        val client = httpClient.cache(cache)
            .retryOnConnectionFailure(true)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(35, TimeUnit.SECONDS)
            .readTimeout(35, TimeUnit.SECONDS)

        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesJacksonConverterFactory(): JacksonConverterFactory {
        return JacksonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }
}