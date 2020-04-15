package com.ajinkya.mvvmdemo.di

import com.ajinkya.mvvmdemo.di.component.AppComponent
import com.ajinkya.mvvmdemo.di.component.DaggerAppComponent
import com.ajinkya.mvvmdemo.di.component.OnBoardingComponent
import com.ajinkya.mvvmdemo.di.component.UserListComponent
import com.ajinkya.mvvmdemo.di.module.OnBoardingModule
import com.ajinkya.mvvmdemo.di.module.UserListModule
import com.ee.core.application.CoreApp
import javax.inject.Singleton

@Singleton
object DaggerComponentProvider {

    private var mAppComponent: AppComponent? = null
    private var mOnBoardingComponent: OnBoardingComponent? = null
    private var mUserListComponent: UserListComponent? = null


    fun appComponent(): AppComponent {
        if (mAppComponent == null)
            mAppComponent =
                DaggerAppComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return mAppComponent as AppComponent
    }


    fun onBoardingComponent(): OnBoardingComponent {
        if (mOnBoardingComponent == null)
            mOnBoardingComponent = mAppComponent!!.addOnBoardingComponent(OnBoardingModule())
        return mOnBoardingComponent as OnBoardingComponent
    }

    fun userListComponent(): UserListComponent {
        if (mUserListComponent == null)
            mUserListComponent = mAppComponent!!.addUserListComponent(UserListModule())
        return mUserListComponent as UserListComponent
    }

}