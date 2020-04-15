package com.ajinkya.mvvmdemo.di.module

import com.ajinkya.mvvmdemo.data.remote.service.UsersService
import com.ajinkya.mvvmdemo.data.repository.implementor.OnBoardingRepositoryImpl
import com.ajinkya.mvvmdemo.data.repository.implementor.OnBoardingRepositoryRemoteImpl
import com.ajinkya.mvvmdemo.data.repository.interactor.OnBoardingRepository
import com.ajinkya.mvvmdemo.di.PerActivity
import com.ajinkya.mvvmdemo.presentation.login.LoginViewModelFactory
import com.ee.core.networking.Scheduler
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
@PerActivity
class OnBoardingModule {

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PerActivity
    fun provideLoginViewModelFactory(
        repository: OnBoardingRepository.Repository,
        compositeDisposable: CompositeDisposable
    ): LoginViewModelFactory = LoginViewModelFactory(repository, compositeDisposable)

    @Provides
    @PerActivity
    fun provideOnBoardingRepository(
        remote: OnBoardingRepository.Remote, scheduler: Scheduler,
        compositeDisposable: CompositeDisposable
    ): OnBoardingRepository.Repository =
        OnBoardingRepositoryImpl(remote, scheduler, compositeDisposable)

    @Provides
    @PerActivity
    fun provideOnBoardingRemote(usersService: UsersService): OnBoardingRepository.Remote =
        OnBoardingRepositoryRemoteImpl(usersService)

}