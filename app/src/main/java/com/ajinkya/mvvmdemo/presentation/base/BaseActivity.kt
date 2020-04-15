package com.ajinkya.mvvmdemo.presentation.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.ajinkya.mvvmdemo.R
import com.ajinkya.mvvmdemo.di.DaggerComponentProvider


abstract class BaseActivity : AppCompatActivity() {

    private var mProgressDialog: Dialog? = null

    private val mComponent by lazy { DaggerComponentProvider.appComponent() }

    fun showProgressDialog() {
        if (mProgressDialog == null || !mProgressDialog!!.isShowing) {
            mProgressDialog = Dialog(this)
            mProgressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mProgressDialog!!.setContentView(R.layout.layout_custom_progress)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
        }
    }

    fun cancelProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }
}