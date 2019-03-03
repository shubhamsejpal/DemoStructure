package com.bazmashop.api.service_module

import retrofit2.Call
import com.bazmashop.Constants
import com.bazmashop.model.ResponseItemListModel
import retrofit2.http.*

interface DashboardModule {

    @GET(Constants.Api.EndUrl.GET_ITEMS)
    fun getItems() : Call<ResponseItemListModel>

}


