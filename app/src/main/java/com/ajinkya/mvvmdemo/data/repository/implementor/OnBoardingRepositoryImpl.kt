package com.ajinkya.mvvmdemo.data.repository.implementor

import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest
import com.ajinkya.mvvmdemo.data.remote.model.response.LoginResponse
import com.ajinkya.mvvmdemo.data.repository.interactor.OnBoardingRepository
import com.ee.core.extension.*
import com.ee.core.networking.Outcome
import com.ee.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class OnBoardingRepositoryImpl(
    private val remote: OnBoardingRepository.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : OnBoardingRepository.Repository {

    override val loginResponse: PublishSubject<Outcome<LoginResponse>> by lazy {
        PublishSubject.create<Outcome<LoginResponse>>()
    }


    override fun doLogin(loginRequest: LoginRequest) {
        loginResponse.loading(true)

        remote.doLogin(loginRequest)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ data ->
                loginResponse.success(data)
            }, { error ->
                loginResponse.failed(error)
            })
            .addTo(compositeDisposable)
    }

}