package com.clearcommon.helper.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.bazmashop.BaseApp
import com.bazmashop.activity.MainActivity
import com.bazmashop.customclasses.Prefs
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.GoogleApiAvailability
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object Util {

    val versionAcronym = "v : "

    fun logout(activity: Activity) {
        Prefs.getInstance(activity).clearPrefs()

        BaseApp.resetRetrofitInstance()

        activity.startActivity(
            Intent(
                activity,
                MainActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
        activity.finish()
    }

    fun rateApp(context: Activity) {
        val intentToAppstore: Intent
        val url = "https://play.google.com/store/apps/details?id=" + context.packageName;
        intentToAppstore = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        try {
            context.startActivity(intentToAppstore)
        } catch (e: Exception) {
            url.openWebPage(context)
        }

    }

    fun shareApp(context: Activity) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=" + context.packageName
        )
        if (intent.resolveActivity(context.packageManager) != null)
            context.startActivity(intent)
        else
            "No app found to perform this action".logD()
    }

    fun composeEmail(
        context: Activity,
        addresses: Array<String>,
        subject: String,
        text: String? = null
    ) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(context.packageManager) != null)
            context.startActivity(intent)
        else
            "You don't have mail client installed".logD()
    }

    fun getHashKey(context: Context): String? {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.DEFAULT)
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

        return null
    }

    fun parseColorRes(context: Context, colorRes: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            context.resources.getColor(colorRes, context.theme)
        else
            context.resources.getColor(colorRes)
    }
}

fun AppCompatActivity.getVersionCode(): String {
    try {
        return Util.versionAcronym + this.packageManager.getPackageInfo(
            this.packageName,
            0
        ).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return ""
}

fun AppCompatActivity.getVersionName(): String {
    try {
        return Util.versionAcronym + this.packageManager.getPackageInfo(
            this.packageName,
            0
        ).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return ""
}

fun String.parseHTML(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    else
        Html.fromHtml(this)
}

fun View.applyTint(color: Int) {
    if (this is ImageView)
        this.setColorFilter(
            ContextCompat.getColor(context, color),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
    else
        this.background.setColorFilter(
            ContextCompat.getColor(context, color),
            PorterDuff.Mode.SRC_IN
        )
}

fun TextView.textColor(context: Context, resTextColor: Int) {
    val color: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        context.resources.getColor(resTextColor, context.theme)
    else
        context.resources.getColor(resTextColor)

    this.setTextColor(color)
}

fun View.clearTint() {
    if (this is ImageView)
        this.colorFilter = null
    else
        this.background.colorFilter = null
}

fun TextView.highlightSubStrings(
    subStringList: List<String>,
    isBold: Boolean,
    color: Int? = null,
    onClick: ((text: String) -> Unit)? = null
) {

    val completeString = this.text.toString()
    val spannableString = SpannableString(completeString)

    subStringList.forEach {

        if (completeString.contains(it, true)) {
            val startPosition = completeString.indexOf(it, ignoreCase = true)
            val endPosition = completeString.lastIndexOf(it, ignoreCase = true) + it.length

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(textView: View) {
                    onClick?.invoke(it)
                }

                override fun updateDrawState(ds: TextPaint?) {
                }
            }

            spannableString.setSpan(
                clickableSpan,
                startPosition,
                endPosition,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )

            if (isBold)
                spannableString.setSpan(StyleSpan(Typeface.BOLD), startPosition, endPosition, 0);

            if (color != null)
                spannableString.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, color)),
                    startPosition,
                    endPosition,
                    0
                )

            this.text = spannableString
//        this.setMovementMethod(LinkMovementMethod.getInstance());
            this.highlightColor = Color.TRANSPARENT
            this.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}

/*
fun AppCompatActivity.arePlayServicesAvailable(REQ_CODE_GOOGLE_SERVICES: Int): Boolean {
    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
    if (status != ConnectionResult.SUCCESS) {
        if (googleApiAvailability.isUserResolvableError(status)) {
            googleApiAvailability.getErrorDialog(this, status, REQ_CODE_GOOGLE_SERVICES).show()
        }
        return false
    }
    return true
}
*/

fun AppCompatActivity.hideStatusBar() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun View.hideKeyboard(context: Context) {
    try {
        (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        if (context.currentFocus != null && context.currentFocus!!.windowToken != null) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                context.currentFocus!!.windowToken, 0
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun View.showKeyboard(context: Context) {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun AppCompatActivity.getDeviceWidth(): Int {
    val displayMetrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun AppCompatActivity.getDeviceHeight(): Int {
    val displayMetrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun String.dialNumber(context: Activity) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$this")
    if (intent.resolveActivity(context.packageManager) != null)
        context.startActivity(intent)
    else
        "No app found to perform call action".logD()
}

fun String.openWebPage(context: Activity) {
    val webpage = Uri.parse(this)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    if (intent.resolveActivity(context.packageManager) != null)
        context.startActivity(intent)
    else
        "No app found to perform this action".logD()
}

fun String.isPackageExists(context: Context): Boolean {
    val pm = context.packageManager
    val packages = pm.getInstalledApplications(0)
    for (packageInfo in packages)
        if (packageInfo.packageName == this) return true
    return false
}



