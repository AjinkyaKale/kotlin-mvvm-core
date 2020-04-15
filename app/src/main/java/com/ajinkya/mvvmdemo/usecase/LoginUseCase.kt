package com.ajinkya.mvvmdemo.usecase

import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest

interface LoginUseCase {

    fun doLogin(loginRequest: LoginRequest)
}