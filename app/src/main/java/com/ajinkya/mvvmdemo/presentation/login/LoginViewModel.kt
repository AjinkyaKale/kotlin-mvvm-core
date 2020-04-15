package com.ajinkya.mvvmdemo.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest
import com.ajinkya.mvvmdemo.data.remote.model.response.LoginResponse
import com.ajinkya.mvvmdemo.data.repository.interactor.OnBoardingRepository
import com.ajinkya.mvvmdemo.usecase.LoginUseCase
import com.ee.core.extension.toLiveData
import com.ee.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(
    private val onBoardingRepository: OnBoardingRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(), LoginUseCase {

    val mLoginResponse: LiveData<Outcome<LoginResponse>> by lazy {
        onBoardingRepository.loginResponse.toLiveData(compositeDisposable)
    }

    override fun doLogin(loginRequest: LoginRequest) {
        onBoardingRepository.doLogin(loginRequest)
    }

}

