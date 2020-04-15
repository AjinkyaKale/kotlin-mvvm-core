package com.ajinkya.mvvmdemo.presentation.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajinkya.mvvmdemo.data.repository.interactor.OnBoardingRepository
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val onBoardingRepository: OnBoardingRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(onBoardingRepository, compositeDisposable) as T
    }
}