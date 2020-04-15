package com.ajinkya.mvvmdemo.presentation.userlist

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajinkya.mvvmdemo.R
import com.ajinkya.mvvmdemo.data.remote.model.Users
import com.ajinkya.mvvmdemo.data.remote.model.response.UserListResponse
import com.ajinkya.mvvmdemo.di.DaggerComponentProvider
import com.ajinkya.mvvmdemo.presentation.base.BaseActivity
import com.ee.core.networking.Outcome
import kotlinx.android.synthetic.main.activity_user_list.*
import javax.inject.Inject

class UserListActivity : BaseActivity() {

    private val mComponent by lazy { DaggerComponentProvider.userListComponent() }

    @Inject
    lateinit var mSharedPref: SharedPreferences

    @Inject
    lateinit var mViewModelFactory: UserListViewModelFactory

    private val mUserListViewModel: UserListViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(UserListViewModel::class.java)
    }

    private lateinit var mUsersArrayList: ArrayList<Users>

    private var mUserListRecyclerAdapter: UserListRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        mComponent.inject(this)

        initObserver()

        fetchUsersList()
    }

    private fun initObserver() {

        mUserListViewModel.mUserListResponse.observe(
            this,
            androidx.lifecycle.Observer<Outcome<UserListResponse>> { outcome ->

                when (outcome) {

                    is Outcome.Progress -> {
                        showProgressDialog()
                    }
                    is Outcome.Success -> {
                        cancelProgressDialog()
                        buildUsersList(outcome.data.usersList)
                    }
                    is Outcome.Failure -> {
                        cancelProgressDialog()
                    }
                }
            })
    }

    private fun fetchUsersList() {
        mUsersArrayList = ArrayList()
        mUserListViewModel.fetchUsers(1)
    }

    private fun buildUsersList(usersList: ArrayList<Users>) {
        mUsersArrayList = usersList
        setDataWithRecyclerView()
    }

    private fun setDataWithRecyclerView() {

        if (mUserListRecyclerAdapter == null) {

            userListRecyclerView.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            mUserListRecyclerAdapter = UserListRecyclerAdapter(mUsersArrayList, this)
            userListRecyclerView.adapter = mUserListRecyclerAdapter

        } else {
            mUserListRecyclerAdapter?.updateAdministrationData(mUsersArrayList)
        }
    }

}

