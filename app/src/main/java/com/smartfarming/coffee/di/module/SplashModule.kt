package com.smartfarming.coffee.di.module

import com.smartfarming.coffee.data.repository.interactor.OnBoardingRepository
import com.smartfarming.coffee.di.PerActivity
import com.smartfarming.coffee.presentation.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@PerActivity
@Module
class SplashModule {

    @Provides
    @PerActivity
    fun provideSplashViewModelFactory(
        repository: OnBoardingRepository.Repository,
        compositeDisposable: CompositeDisposable
    ): SplashViewModelFactory = SplashViewModelFactory(repository, compositeDisposable)

}