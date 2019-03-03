package com.bazmashop.dashboard.model

import android.view.View
import com.bazmashop.activity.MainActivity
import com.bazmashop.api.OnApiResponseListener
import com.bazmashop.dashboard.presenter.ShopFragPresenter
import com.bazmashop.dashboard.view.ShopFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shop.*
import retrofit2.Call

class ShopFragModel(private var act: ShopFragment) : OnApiResponseListener<Any>,
        ShopFragPresenter {

    var issueListType: String? = ""

    override fun onResponseComplete(clsGson: Any?, requestCode: Int, responseCode: Int) {
        act.animation_view.visibility = View.INVISIBLE
        act.rvItemList.visibility = View.VISIBLE
        act.llFilter.visibility = View.VISIBLE
        act.onResponseComplete(clsGson, requestCode, responseCode)
    }

    private var api: Call<*>? = null

    private val isValid: Boolean
        get() = true

    override fun getItem() {
        if (isValid) {
            act.animation_view.visibility = View.VISIBLE
            act.rvItemList.visibility = View.INVISIBLE
            act.llFilter.visibility = View.INVISIBLE
            api = act.apiCall.getItems(this, act.javaClass)
        } else
            act.showSnackbar("", (act as MainActivity).llRoot)
    }

    override fun onResponseError(errorMessage: String, requestCode: Int, responseCode: Int) {
        act.animation_view.visibility = View.INVISIBLE
        act.onResponseError(errorMessage)
    }
}
