package com.bazmashop.api

import android.util.Log
import com.google.gson.JsonElement

class ResponseError {
    var message: String? = null
    private val error: JsonElement? = null

    val errMsg: String?
        get() {
            var msg = ""
            try {
                if (error != null && error.isJsonObject) {
                    val obj = error.asJsonObject
                    val entrySet = obj.entrySet()
                    for ((key, value) in entrySet) {
                        if ((value as JsonElement).isJsonArray) {
                            val arr = value.asJsonArray
                            msg += arr.get(0).asString
                            break
                        }
                        Log.i("error", "getErrMsg: $key $msg")
                    }
                }
                return msg
            } catch (e: Exception) {
                return null
            }

        }

}