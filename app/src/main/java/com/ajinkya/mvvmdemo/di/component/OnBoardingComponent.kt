package com.ajinkya.mvvmdemo.di.component


import com.ajinkya.mvvmdemo.di.PerActivity
import com.ajinkya.mvvmdemo.di.module.OnBoardingModule
import com.ajinkya.mvvmdemo.presentation.login.LoginActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [OnBoardingModule::class])
interface OnBoardingComponent {

    fun inject(loginActivity: LoginActivity)
}