package com.ajinkya.mvvmdemo.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ee.core.application.CoreApp

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val isNetworkAvailable = NetworkUtil.isNetworkAvailable(p0!!)
        CoreApp.isInternet = isNetworkAvailable
    }
}