package com.clearcommon.helper.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.bazmashop.R


class ProgressBarDialog(context: Context) {
    var dialog: Dialog? = null

    init {
        dialog = Dialog(context)
        dialog = Dialog(context, android.R.style.Theme_Translucent)

        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.progressbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            dialog!!.window!!.statusBarColor = Color.parseColor("#80000000")
        }
    }

    fun showDialog() {
        if (dialog != null) {
            dialog!!.setCancelable(false)
            dialog!!.show()
        }
    }


    fun dismissDialog() {
        if (dialog != null)
            dialog!!.dismiss()
    }


}
