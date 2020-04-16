package com.ajinkya.mvvmdemo.presentation.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ajinkya.mvvmdemo.R
import com.ajinkya.mvvmdemo.common.saveAccessToken
import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest
import com.ajinkya.mvvmdemo.data.remote.model.response.LoginResponse
import com.ajinkya.mvvmdemo.di.DaggerComponentProvider
import com.ajinkya.mvvmdemo.presentation.base.BaseActivity
import com.ajinkya.mvvmdemo.presentation.userlist.UserListActivity
import com.ee.core.extension.toast
import com.ee.core.networking.Outcome
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    private val mComponent by lazy { DaggerComponentProvider.onBoardingComponent() }

    private val mViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel::class.java)
    }

    @Inject
    lateinit var mSharedPreferences: SharedPreferences

    @Inject
    lateinit var mViewModelFactory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mComponent.inject(this)

        initOnClickListener()

        initObserver()
    }

    private fun initOnClickListener() {

        loginButtonLogin.setOnClickListener {

            val loginRequest = getDataFromFields()

            if (isValidUserInput(loginRequest)) {
                callDoLogin(loginRequest)
            }
        }
    }

    private fun initObserver() {

        initLoginResponseObserver()
    }

    private fun initLoginResponseObserver() {

        mViewModel.mLoginResponse.observe(this, Observer<Outcome<LoginResponse>> { outcome ->

            when (outcome) {

                is Outcome.Progress -> {
                    showProgressDialog()
                }

                is Outcome.Success -> {
                    cancelProgressDialog()
                    saveLoginDataToSharedPref(outcome.data)
                    showSnackBar("Login successful !")
                    launchNextScreen()
                }

                is Outcome.Failure -> {
                    cancelProgressDialog()
                    showSnackBar("User does not exist !")
                }
            }
        })
    }

    private fun getDataFromFields(): LoginRequest {

        return LoginRequest(
            loginEditTextLoginEmailId.text.toString().trim(),
            loginEditTextLoginPassword.text.toString()
        )
    }

    private fun callDoLogin(loginRequest: LoginRequest) {
        mViewModel.doLogin(loginRequest)
    }

    private fun saveLoginDataToSharedPref(loginResponse: LoginResponse) {

        mSharedPreferences.saveAccessToken(loginResponse.token)
    }

    private fun launchNextScreen() {

        Handler().postDelayed({

            startActivity(Intent(this, UserListActivity::class.java))
            finish()

        }, 4000)
    }

    private fun isValidUserInput(dataFromFields: LoginRequest): Boolean {

        if (dataFromFields.email.isEmpty()) {
            toast(getString(R.string.email_nil_error))
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(dataFromFields.email).matches()) {
            toast(getString(R.string.email_invalid_error))
            return false
        }
        if (dataFromFields.password.isEmpty()) {
            toast(getString(R.string.password_nil_error))
            return false
        }
        return true
    }

    private fun showSnackBar(title: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), title, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

        textView.setTextColor(Color.RED)
        textView.textSize = 20f
        snackBar.show()
    }

}