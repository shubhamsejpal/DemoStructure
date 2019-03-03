package com.clearcommon.helper.recycler_view_util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewHelper {

    private var rv: RecyclerView? = null
    private var lm: RecyclerView.LayoutManager? = null
    private var noDataView: View? = null
    private var loadMoreView: View? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var adapter: RecyclerView.Adapter<*>? = null

    fun init(rv: RecyclerView, lm: RecyclerView.LayoutManager, noDataView: View? = null, loadMoreView: View? = null) {
        this.rv = rv
        this.lm = lm
        this.noDataView = noDataView
        this.loadMoreView = loadMoreView

        this.rv!!.layoutManager = lm
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        this.adapter = adapter
        this.rv!!.adapter = adapter

        manageItemCount()
    }

    fun notifyAdapter() {
        adapter!!.notifyDataSetChanged()
        manageItemCount()
    }

    private fun manageItemCount() {
        if (adapter == null || adapter!!.itemCount == 0)
            showNoData()
        else
            hideNoData()
    }

    private fun hideNoData() {
        noDataView?.visibility = View.GONE
        rv!!.visibility = View.VISIBLE
    }

    private fun showNoData() {
        noDataView?.visibility = View.VISIBLE
        rv!!.visibility = View.GONE
    }

    fun setDecoration(spaceItemDecoration: SpaceItemDecoration) {
        rv!!.addItemDecoration(spaceItemDecoration)
    }

    fun setOnLoadMoreListener(onLoadingListener: OnLoadMoreListener) {
        onLoadMoreListener = onLoadingListener
        scrollListener = object : EndlessRecyclerViewScrollListener(lm!!, lm is LinearLayoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (onLoadMoreListener != null) {
                    loadMoreView!!.visibility = View.VISIBLE
                    onLoadMoreListener!!.onLoadMore(page, totalItemsCount)
                }
            }
        }
        rv!!.addOnScrollListener(scrollListener!!)
    }

    //Not called before set adapter
    fun getAdapter(): RecyclerView.Adapter<*> = adapter!!

}
