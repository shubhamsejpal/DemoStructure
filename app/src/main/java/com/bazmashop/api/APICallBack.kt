package com.bazmashop.api

import android.app.Activity
import com.bazmashop.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APICallBack<T> internal constructor(
    private val listener: OnApiResponseListener<T>?,
    private val requestCode: Int
) : Callback<T> {


    companion object {
        var activity: Activity? = null
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        //        Util.dismissProgress();

//        ApiCall.calls!!.remove(call)
        if (isSuccess(response)) {
            try {
                listener?.onResponseComplete(response.body(), requestCode, response.code())
            } catch (e: Exception) {
                listener?.onResponseError(e.localizedMessage, requestCode, 0)
                e.printStackTrace()
            }

        }
    }

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        //        Log.e("onFailure-> ", throwable.getMessage());
        //        Util.dismissProgress();

//        ApiCall.calls!!.remove(call)
        if (throwable.message != null && throwable.message!!.toLowerCase().contains("no address associated with hostname")) {
            listener?.onResponseError("No Internet Connection", requestCode, 0)
        } else if (throwable.message != null && throwable.message!!.toLowerCase().contains("failed to connect to")) {
            listener?.onResponseError("No Internet Connection", requestCode, 0)
        } else if (throwable.message != null)
            listener!!.onResponseError(throwable.message!!, requestCode, 0)
    }

    private fun isSuccess(response: Response<T>): Boolean {
        if (!response.isSuccessful) {
            try {

                if (response.code() == Constants.Api.ResponseCode.UNAUTHORIZED_CODE) {
//                    unauthorized()
                } else {
                    var errorMsg = ""

                    if (response.message() != null) {
                        val errorModel1 = ErrorUtils.parseError(response)
                        if (errorModel1 != null)
                            listener?.onResponseError(
                                errorModel1.getMessage(),
                                requestCode, response.code()
                            )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false

        } else if (response.body() == null) {
            try {
                listener?.onResponseError(
                    "Please check logcat" + response.code() + " " + response.message(),
                    requestCode,
                    response.code()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }
        return true
    }

    private fun unauthorized() {
    }

}