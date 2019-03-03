package com.bazmashop.main.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.support.design.widget.Snackbar



abstract class BaseAct : AppCompatActivity() {

//    private var progressBarDialog: ProgressBarDialog? = null
    lateinit var rootView: View
//    lateinit var apiCall: ApiCall
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    protected abstract val className: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTasks()
    }

    external fun loadAPI(): String

    private fun initTasks() {
//        apiCall = ApiCall()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun setContentView(layout: Int, isFullScreen: Boolean) {
        rootView = window.decorView.findViewById<View>(android.R.id.content)
        setContentView(layout)
    }

    override fun onDestroy() {
        super.onDestroy()
//        apiCall.cancelAllCall(className)
    }

    fun showSnackbar(message:String, view : View){
        val snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}
