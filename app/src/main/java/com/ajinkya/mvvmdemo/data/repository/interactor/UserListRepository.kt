package com.ajinkya.mvvmdemo.data.repository.interactor

import com.ajinkya.mvvmdemo.data.remote.model.response.UserListResponse
import com.ee.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface UserListRepository {

    interface Repository {

        val userListResponse: PublishSubject<Outcome<UserListResponse>>

        fun fetchUsers(pageNumber: Int)
    }

    interface Remote {

        fun fetchUsers(pageNumber: Int): Flowable<UserListResponse>
    }

}