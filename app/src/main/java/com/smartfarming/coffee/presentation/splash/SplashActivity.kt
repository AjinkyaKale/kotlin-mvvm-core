package com.smartfarming.coffee.presentation.splash

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.smartfarming.coffee.R
import com.smartfarming.coffee.di.DaggerComponentProvider
import com.smartfarming.coffee.presentation.login.RegistrationActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null

    private val mComponent by lazy { DaggerComponentProvider.splashComponent() }

    @Inject
    lateinit var mViewModelFactory: SplashViewModelFactory

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(SplashViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mComponent.inject(this)

        openScreen()
    }

    /**
     * Function with Handler to delay the splash
     */
    fun openScreen() {

        Handler().postDelayed(
            {

                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()

            }, mViewModel.time
        )
    }


}