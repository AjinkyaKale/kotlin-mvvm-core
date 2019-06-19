package com.smartfarming.coffee.presentation.splash

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.smartfarming.coffee.data.repository.interactor.OnBoardingRepository
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(
    private val onBoardingRepository: OnBoardingRepository.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(onBoardingRepository, compositeDisposable) as T
    }
}