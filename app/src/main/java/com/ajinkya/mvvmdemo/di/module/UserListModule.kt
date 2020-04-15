package com.ajinkya.mvvmdemo.di.module

import com.ajinkya.mvvmdemo.data.remote.service.UsersService
import com.ajinkya.mvvmdemo.data.repository.implementor.UserListRepositoryImpl
import com.ajinkya.mvvmdemo.data.repository.implementor.UserListRepositoryRemoteImpl
import com.ajinkya.mvvmdemo.data.repository.interactor.UserListRepository
import com.ajinkya.mvvmdemo.di.PerActivity
import com.ajinkya.mvvmdemo.presentation.userlist.UserListViewModelFactory
import com.ee.core.networking.Scheduler
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@PerActivity
@Module
class UserListModule {

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PerActivity
    fun provideUsersListViewModelFactory(
        repository: UserListRepository.Repository,
        compositeDisposable: CompositeDisposable
    ): UserListViewModelFactory = UserListViewModelFactory(repository, compositeDisposable)

    @Provides
    @PerActivity
    fun provideUsersListRepository(
        remote: UserListRepository.Remote, scheduler: Scheduler,
        compositeDisposable: CompositeDisposable
    ): UserListRepository.Repository =
        UserListRepositoryImpl(remote, scheduler, compositeDisposable)

    @Provides
    @PerActivity
    fun provideUsersListRemote(usersService: UsersService): UserListRepository.Remote =
        UserListRepositoryRemoteImpl(usersService)

}