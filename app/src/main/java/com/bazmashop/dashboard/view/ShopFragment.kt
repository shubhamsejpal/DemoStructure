package com.bazmashop.dashboard.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bazmashop.Constants

import com.bazmashop.R
import com.bazmashop.activity.MainActivity
import com.bazmashop.adapter.RecyclerViewAdapter
import com.bazmashop.dashboard.model.ShopFragModel
import com.bazmashop.dashboard.presenter.ShopFragPresenter
import com.bazmashop.main.base.BaseFrag
import com.bazmashop.model.ResponseItemListModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * A simple [Fragment] subclass.
 */
class ShopFragment : BaseFrag() {

    lateinit var model: ShopFragPresenter
    lateinit var response: ResponseItemListModel
    private var mAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initTasks()
        listeners()
    }

    private fun initTasks() {

        model = ShopFragModel(this)

        model.getItem()
    }

    private fun listeners() {
        rlSort.setOnClickListener{
            showSnackbar("Sort Clicked",(activity as MainActivity).llRoot)
        }
        tvRefine.setOnClickListener{
            showSnackbar("Refined Clicked",(activity as MainActivity).llRoot)
        }
    }

    private fun setViews() {
        rvItemList.layoutManager = GridLayoutManager(activity, 2)

        mAdapter = RecyclerViewAdapter(context!!, response.data.products)

        rvItemList.adapter = mAdapter

    }

    fun onResponseComplete(clsGson: Any?, requestCode: Int, responseCode: Int) {
        if (requestCode == Constants.Api.RequestCode.GET_ITEMS)
            if (responseCode == 200) {
                response = clsGson as ResponseItemListModel

                (activity as MainActivity).tvItemCount.text = response.data.totalProducts + " Items"
                setViews()
            }
    }

    fun onResponseError(errorMessage: String) {
        showSnackbar(errorMessage, (activity as MainActivity).llRoot)
    }
}