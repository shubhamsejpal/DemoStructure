package com.bazmashop.customclasses

import android.content.Context
import android.content.SharedPreferences
import com.bazmashop.BaseApp
import com.bazmashop.Constants
import com.bazmashop.R
import com.google.gson.Gson

/**
 * Created by shubhamsejpal on 03/03/19.
 */

class Prefs(context: Context) {

    private val prefs: SharedPreferences
    private val prefEditor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), 0)
        prefEditor = prefs.edit()
    }

    var authToken: String?
        get() = prefs.getString(Constants.Prefs.AUTH_TOKEN, "")
        set(authToken) {
            prefEditor.putString(Constants.Prefs.AUTH_TOKEN, authToken)
            BaseApp.setToken(authToken)
            prefEditor.apply()
        }

    var fcmToken: String?
        get() = prefs.getString(Constants.Prefs.AUTH_TOKEN, "")
        set(authToken) {
            prefEditor.putString(Constants.Prefs.AUTH_TOKEN, authToken)
            BaseApp.setToken(authToken)
            prefEditor.apply()
        }

/*
    var userInfo: LoginModel
        get() = Gson().fromJson<LoginModel>(prefs.getString(Constants.Prefs.USER_INFO, ""), LoginModel::class.java!!)
        set(data) {
            prefEditor.putString(Constants.Prefs.USER_INFO, Gson().toJson(data))
            prefEditor.apply()
        }
*/

/*
    var numberofproperty: Int
        get() = prefs.getInt(Constants.Prefs.NUMBEROFPROPERTY, 1)
        set(id) {
            prefEditor.putInt(Constants.Prefs.NUMBEROFPROPERTY, id)
            prefEditor.apply()
        }
*/

/*
    var createAssociation: Boolean
        get() = prefs.getBoolean(Constants.Prefs.CREATEASSOCIATION, false)
        set(id) {
            prefEditor.putBoolean(Constants.Prefs.CREATEASSOCIATION, id)
            prefEditor.apply()
        }
*/

    fun clearPrefs() {
        prefEditor.clear()
        prefEditor.apply()
    }

    companion object {

        var mPrefs: Prefs? = null

        fun getInstance(context: Context): Prefs {
            if (mPrefs == null)
                mPrefs = Prefs(context)
            return mPrefs as Prefs
        }
    }
}
