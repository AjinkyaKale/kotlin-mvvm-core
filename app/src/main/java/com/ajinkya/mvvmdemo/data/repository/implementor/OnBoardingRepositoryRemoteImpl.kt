package com.ajinkya.mvvmdemo.data.repository.implementor

import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest
import com.ajinkya.mvvmdemo.data.remote.model.response.LoginResponse
import com.ajinkya.mvvmdemo.data.remote.service.UsersService
import com.ajinkya.mvvmdemo.data.repository.interactor.OnBoardingRepository
import io.reactivex.Flowable

class OnBoardingRepositoryRemoteImpl(private val usersService: UsersService) :
    OnBoardingRepository.Remote {

    override fun doLogin(loginRequest: LoginRequest): Flowable<LoginResponse> {
        return usersService.doLogin(loginRequest)
    }
}