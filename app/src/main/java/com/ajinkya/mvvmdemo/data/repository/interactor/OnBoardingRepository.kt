package com.ajinkya.mvvmdemo.data.repository.interactor

import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest
import com.ajinkya.mvvmdemo.data.remote.model.response.LoginResponse
import com.ee.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface OnBoardingRepository {

    interface Repository {

        val loginResponse: PublishSubject<Outcome<LoginResponse>>

        fun doLogin(loginRequest: LoginRequest)
    }

    interface Remote {

        fun doLogin(loginRequest: LoginRequest): Flowable<LoginResponse>
    }

}