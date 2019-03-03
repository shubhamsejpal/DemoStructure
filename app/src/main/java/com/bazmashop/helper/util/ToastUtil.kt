package com.clearcommon.helper.util

import android.graphics.Color
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bazmashop.R
import com.clearcommon.helper.util.ToastUtil.toastDurationInMilliSeconds

object ToastUtil {
    val SNACK_ERROR_COLOR = "#dd5a5a"
    val SNACK_SUCCESS_COLOR = "#87cc6c"
    var toastDurationInMilliSeconds= 5000L
}

fun View.errorSnackbar(message: String) {

    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    val view = toast.getView()
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
    view.setBackgroundResource(R.drawable.toast_error)
//    val text = view.findViewById(android.R.id.message) as TextView
//    text.setPadding(25, 0, 25, 0)
    /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
    // Set the countdown to display the toast
    val toastCountDown: CountDownTimer
    toastCountDown = object : CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
        override fun onTick(millisUntilFinished: Long) {
            toast.show()
        }
        override fun onFinish() {
            toast.cancel()
        }
    }
    // Show the toast and starts the countdown
    toast.show()
    toastCountDown.start()
/*
    val snackbar = TopSnackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(Color.parseColor(ToastUtil.SNACK_ERROR_COLOR))
    snackbar.show()
*/
}

fun View.successSnackbar(message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    val view = toast.getView()
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
    view.setBackgroundResource(R.drawable.toast_success)
//    val text = view.findViewById(android.R.id.message) as TextView
//    text.setPadding(25, 0, 25, 0)
    /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
    // Set the countdown to display the toast
    val toastCountDown: CountDownTimer
    toastCountDown = object : CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
        override fun onTick(millisUntilFinished: Long) {
            toast.show()
        }
        override fun onFinish() {
            toast.cancel()
        }
    }
    // Show the toast and starts the countdown
    toast.show()
    toastCountDown.start()
}