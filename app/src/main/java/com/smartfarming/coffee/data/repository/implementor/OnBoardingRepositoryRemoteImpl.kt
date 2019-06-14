package com.smartfarming.coffee.data.repository.implementor

import com.smartfarming.coffee.data.remote.model.request.TokenRequest
import com.smartfarming.coffee.data.remote.model.response.TokenResponse
import com.smartfarming.coffee.data.remote.service.SmartFarmingService
import com.smartfarming.coffee.data.repository.interactor.OnBoardingRepository
import io.reactivex.Flowable

class OnBoardingRepositoryRemoteImpl(private val smartFarmingService: SmartFarmingService) :
    OnBoardingRepository.Remote {

    override fun getAccessToken(tokenRequest: TokenRequest): Flowable<TokenResponse> {
        return smartFarmingService.getAccessToken(tokenRequest)
    }
}