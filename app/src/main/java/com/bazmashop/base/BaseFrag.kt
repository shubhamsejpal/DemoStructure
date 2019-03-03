package com.bazmashop.main.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bazmashop.api.ApiCall

abstract class BaseFrag : Fragment() {
    private var rootView: View? = null
    lateinit var apiCall: ApiCall
    internal lateinit var baseAct: BaseAct

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        baseAct = (activity as BaseAct)

    }

    private fun initTasks() {
        apiCall = ApiCall()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initTasks()

    }

    fun setContentView(inflater: LayoutInflater, layout: Int, container: ViewGroup?): View? {
        rootView = inflater.inflate(layout, container, false)

        return rootView
    }

    fun showSnackbar(message: String, view: View) {
        baseAct.showSnackbar(message, view)
    }
}
