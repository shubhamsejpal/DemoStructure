package com.bazmashop.api


import com.bazmashop.Constants
import com.bazmashop.model.ResponseItemListModel
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import kotlin.collections.HashMap

class ApiCall {


    companion object {

        var calls: MutableMap<String, ArrayList<Call<Any>>>? = HashMap()
    }

    fun getItems(
        listener: OnApiResponseListener<Any>,
        tag: Class<*>
    ): Call<ResponseItemListModel> {
        val callback = Api.dashboardModule().getItems()
        callback.enqueue(APICallBack(listener, Constants.Api.RequestCode.GET_ITEMS) as Callback<ResponseItemListModel>)
        add(tag.simpleName, callback as Call<Any>)
        return callback
    }

    private fun add(tag: String, call: Call<Any>) {
        var callMap = calls!![tag]
        if (callMap == null)
            callMap = ArrayList()
        callMap.add(call)
    }
}
