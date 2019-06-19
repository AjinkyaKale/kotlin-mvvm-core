package com.smartfarming.coffee.presentation.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ee.core.networking.Outcome
import com.smartfarming.coffee.BuildConfig
import com.smartfarming.coffee.R
import com.smartfarming.coffee.common.PreferenceHelper
import com.smartfarming.coffee.data.remote.model.request.TokenRequest
import com.smartfarming.coffee.data.remote.model.response.TokenResponse
import com.smartfarming.coffee.di.DaggerComponentProvider
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val mComponent by lazy { DaggerComponentProvider.splashComponent() }

    @Inject
    lateinit var mSharedPreferences: SharedPreferences

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mComponent.inject(this)

        setFullScreenView()

        initObserver()

        if (mSharedPreferences.getBoolean(PreferenceHelper.PREF_IS_USER_REGISTERED, false)) {
            //Todo
        } else {
            getAccessToken()
        }
    }

    private fun getAccessToken() {
        val tokenRequestModel = TokenRequest(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)

        mViewModel.getAccessToken(tokenRequestModel)
    }

    private fun initObserver() {

        initAccessTokenResponseObserver()
    }

    private fun initAccessTokenResponseObserver() {

        mViewModel.mAccessTokenResponse.observe(this, Observer<Outcome<TokenResponse>> { outcome ->

            when (outcome) {

                is Outcome.Progress -> {

                }

                is Outcome.Success -> {

                }

                is Outcome.Failure -> {

                }
            }
        })
    }

    private fun setFullScreenView() {
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }


}