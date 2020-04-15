package com.ajinkya.mvvmdemo.di.component


import com.ajinkya.mvvmdemo.di.PerActivity
import com.ajinkya.mvvmdemo.di.module.UserListModule
import com.ajinkya.mvvmdemo.presentation.userlist.UserListActivity
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = [UserListModule::class])
interface UserListComponent {

    fun inject(userListActivity: UserListActivity)
}