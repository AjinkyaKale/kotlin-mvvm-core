package com.ajinkya.mvvmdemo.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajinkya.mvvmdemo.data.remote.model.response.UserListResponse
import com.ajinkya.mvvmdemo.data.repository.interactor.UserListRepository
import com.ajinkya.mvvmdemo.usecase.UserListUseCase
import com.ee.core.extension.toLiveData
import com.ee.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class UserListViewModel(
    private val userListRepository: UserListRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(), UserListUseCase {

    val mUserListResponse: LiveData<Outcome<UserListResponse>> by lazy {
        userListRepository.userListResponse.toLiveData(compositeDisposable)
    }

    override fun fetchUsers(pageNumber: Int) {
        userListRepository.fetchUsers(pageNumber)
    }

}

