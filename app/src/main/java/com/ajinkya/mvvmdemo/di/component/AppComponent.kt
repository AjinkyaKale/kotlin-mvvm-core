package com.ajinkya.mvvmdemo.di.component

import com.ajinkya.mvvmdemo.UsersApplication
import com.ajinkya.mvvmdemo.common.SharedPreferenceHelper
import com.ajinkya.mvvmdemo.data.remote.service.UsersService
import com.ajinkya.mvvmdemo.di.AppScope
import com.ajinkya.mvvmdemo.di.module.AppModule
import com.ajinkya.mvvmdemo.di.module.OnBoardingModule
import com.ajinkya.mvvmdemo.di.module.UserListModule
import com.ajinkya.mvvmdemo.presentation.base.BaseActivity
import com.ee.core.di.CoreComponent
import dagger.Component

@AppScope
@Component(dependencies = [CoreComponent::class], modules = [AppModule::class])
interface AppComponent {

    fun sharedPreferenceHelper(): SharedPreferenceHelper
    fun userService(): UsersService


    fun inject(application: UsersApplication)

    fun inject(baseActivity: BaseActivity)

    // sub-components
    fun addOnBoardingComponent(onBoardingModule: OnBoardingModule): OnBoardingComponent
    fun addUserListComponent(userListModule: UserListModule): UserListComponent


}