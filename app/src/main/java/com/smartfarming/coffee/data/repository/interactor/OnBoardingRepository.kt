package com.smartfarming.coffee.data.repository.interactor

import com.ee.core.networking.Outcome
import com.smartfarming.coffee.data.remote.model.request.TokenRequest
import com.smartfarming.coffee.data.remote.model.response.TokenResponse
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface OnBoardingRepository {

    interface Repository {

        val tokenResponse: PublishSubject<Outcome<TokenResponse>>

        fun getAccessToken(tokenRequest: TokenRequest)

//        val nonSbqResponse: PublishSubject<Outcome<DefaultResponse>>
//
//        val loginResponse: PublishSubject<Outcome<LoginResponse>>
//
//        val registerDeviceResponse: PublishSubject<Outcome<DefaultResponse>>
//
//        val profileResponse: PublishSubject<Outcome<ProfileResponse>>
//
//        fun submitTellUsAboutYou(request: TellUsAboutYouRequest)
//
//        fun doLogin(request: LoginRequest)
//
//        fun registerDevice(accessToken: String, request: RegisterDeviceRequest)
//
//        fun getProfileData(accessToken: String)
    }

    interface Remote {

        fun getAccessToken(tokenRequest: TokenRequest): Flowable<TokenResponse>

//        fun submitTellUsAboutYou(request: TellUsAboutYouRequest): Flowable<DefaultResponse>
//
//        fun doLogin(request: LoginRequest): Flowable<LoginResponse>
//
//        fun registerDevice(accessToken: String, request: RegisterDeviceRequest): Flowable<DefaultResponse>
//
//        fun getProfileData(accessToken: String): Flowable<ProfileResponse>
    }

}