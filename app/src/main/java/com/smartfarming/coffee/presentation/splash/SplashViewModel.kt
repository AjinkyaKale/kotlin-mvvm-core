package com.smartfarming.coffee.presentation.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ee.core.extension.toLiveData
import com.ee.core.networking.Outcome
import com.smartfarming.coffee.data.remote.model.request.TokenRequest
import com.smartfarming.coffee.data.remote.model.response.TokenResponse
import com.smartfarming.coffee.data.repository.interactor.OnBoardingRepository
import com.smartfarming.coffee.usecase.SplashUseCase
import io.reactivex.disposables.CompositeDisposable

class SplashViewModel(
    private val onBoardingRepository: OnBoardingRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(), SplashUseCase {

    val mAccessTokenResponse: LiveData<Outcome<TokenResponse>> by lazy {
        onBoardingRepository.tokenResponse.toLiveData(compositeDisposable)
    }

    override fun getAccessToken(tokenRequest: TokenRequest) {
        onBoardingRepository.getAccessToken(tokenRequest)
    }

    /*val mLoginResponse: LiveData<Outcome<LoginResponse>> by lazy {
        onBoardingRepository.loginResponse.toLiveData(compositeDisposable)
    }

    val mProfileResponse: LiveData<Outcome<ProfileResponse>> by lazy {
        onBoardingRepository.profileResponse.toLiveData(compositeDisposable)
    }

    val mRegisterDeviceResponse: LiveData<Outcome<DefaultResponse>> by lazy {
        onBoardingRepository.registerDeviceResponse.toLiveData(compositeDisposable)
    }

    override fun doLogin(request: LoginRequest) {
        onBoardingRepository.doLogin(request)
    }

    override fun registerDevice(accessToken: String, request: RegisterDeviceRequest) {
        onBoardingRepository.registerDevice(accessToken, request)
    }

    override fun getProfileData(accessToken: String) {
        onBoardingRepository.getProfileData(accessToken)
    }*/
}

