package com.smartfarming.coffee.data.repository.implementor

import android.content.SharedPreferences
import com.ee.core.extension.*
import com.ee.core.networking.Outcome
import com.ee.core.networking.Scheduler
import com.smartfarming.coffee.data.remote.model.request.TokenRequest
import com.smartfarming.coffee.data.remote.model.response.TokenResponse
import com.smartfarming.coffee.data.repository.interactor.OnBoardingRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class OnBoardingRepositoryImpl(
    private val remote: OnBoardingRepository.Remote,
    private val scheduler: Scheduler,
    private val sharedPreferences: SharedPreferences,
    private val compositeDisposable: CompositeDisposable
) : OnBoardingRepository.Repository {

    override val tokenResponse: PublishSubject<Outcome<TokenResponse>> by lazy {
        PublishSubject.create<Outcome<TokenResponse>>()
    }


    override fun getAccessToken(tokenRequest: TokenRequest) {
        tokenResponse.loading(true)

        remote.getAccessToken(tokenRequest)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ data ->
                tokenResponse.success(data)
            }, { error ->
                tokenResponse.failed(error)
            })
            .addTo(compositeDisposable)
    }

}