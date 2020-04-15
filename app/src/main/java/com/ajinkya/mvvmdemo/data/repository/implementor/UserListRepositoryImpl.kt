package com.ajinkya.mvvmdemo.data.repository.implementor

import com.ajinkya.mvvmdemo.data.remote.model.response.UserListResponse
import com.ajinkya.mvvmdemo.data.repository.interactor.UserListRepository
import com.ee.core.extension.*
import com.ee.core.networking.Outcome
import com.ee.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class UserListRepositoryImpl(
    private val remote: UserListRepository.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : UserListRepository.Repository {

    override val userListResponse: PublishSubject<Outcome<UserListResponse>> by lazy {
        PublishSubject.create<Outcome<UserListResponse>>()
    }

    override fun fetchUsers(pageNumber: Int) {
        userListResponse.loading(true)

        remote.fetchUsers(pageNumber)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ data ->
                userListResponse.success(data)
            }, { error ->
                userListResponse.failed(error)
            })
            .addTo(compositeDisposable)
    }
}