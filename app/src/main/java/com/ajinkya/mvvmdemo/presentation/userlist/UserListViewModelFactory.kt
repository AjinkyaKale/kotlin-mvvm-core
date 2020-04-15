package com.ajinkya.mvvmdemo.presentation.userlist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajinkya.mvvmdemo.data.repository.interactor.UserListRepository
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class UserListViewModelFactory(
    private val userListRepository: UserListRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(userListRepository, compositeDisposable) as T
    }
}