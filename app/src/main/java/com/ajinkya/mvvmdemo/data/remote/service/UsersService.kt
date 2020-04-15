package com.ajinkya.mvvmdemo.data.remote.service

import com.ajinkya.mvvmdemo.data.remote.model.request.LoginRequest
import com.ajinkya.mvvmdemo.data.remote.model.response.LoginResponse
import com.ajinkya.mvvmdemo.data.remote.model.response.UserListResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * All API calls will be handled from here
 */
interface UsersService {

    @POST("api/login")
    fun doLogin(@Body request: LoginRequest): Flowable<LoginResponse>

    @GET("api/users")
    fun fetchUsers(@Query("page") pageNumber: Int): Flowable<UserListResponse>

}

