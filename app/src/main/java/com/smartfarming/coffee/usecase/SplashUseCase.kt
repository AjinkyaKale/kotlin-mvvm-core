package com.smartfarming.coffee.usecase

import com.smartfarming.coffee.data.remote.model.request.TokenRequest

interface SplashUseCase {

    fun getAccessToken(tokenRequest: TokenRequest)
}