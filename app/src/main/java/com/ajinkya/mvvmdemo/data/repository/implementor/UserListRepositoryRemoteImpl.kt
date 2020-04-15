package com.ajinkya.mvvmdemo.data.repository.implementor

import com.ajinkya.mvvmdemo.data.remote.model.response.UserListResponse
import com.ajinkya.mvvmdemo.data.remote.service.UsersService
import com.ajinkya.mvvmdemo.data.repository.interactor.UserListRepository
import io.reactivex.Flowable

class UserListRepositoryRemoteImpl(private val usersService: UsersService) :
    UserListRepository.Remote {


    override fun fetchUsers(pageNumber: Int): Flowable<UserListResponse> {
        return usersService.fetchUsers(pageNumber)
    }
}