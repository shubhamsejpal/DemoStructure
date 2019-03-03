package com.bazmashop.api

interface OnApiResponseListener<T> {

    fun onResponseComplete(clsGson: T?, requestCode: Int, responseCode: Int)

    fun onResponseError(errorMessage: String, requestCode: Int, responseCode: Int)
}
