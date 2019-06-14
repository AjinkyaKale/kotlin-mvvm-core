package com.smartfarming.coffee.data.remote.service

import com.smartfarming.coffee.data.remote.model.request.TokenRequest
import com.smartfarming.coffee.data.remote.model.response.TokenResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * All API calls will be handled from here
 */
interface SmartFarmingService {

    @POST("api/v1/token")
    fun getAccessToken(@Body request: TokenRequest): Flowable<TokenResponse>

}

