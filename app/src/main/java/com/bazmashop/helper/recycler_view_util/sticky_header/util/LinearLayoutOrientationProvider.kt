package com.clearcommon.helper.recycler_view_util.sticky_header.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * OrientationProvider for ReyclerViews who use a LinearLayoutManager
 */
class LinearLayoutOrientationProvider : OrientationProvider {

    override fun getOrientation(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager
        throwIfNotLinearLayoutManager(layoutManager!!)
        return (layoutManager as LinearLayoutManager).orientation
    }

    override fun isReverseLayout(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager
        throwIfNotLinearLayoutManager(layoutManager!!)
        return (layoutManager as LinearLayoutManager).reverseLayout
    }

    private fun throwIfNotLinearLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        if (layoutManager !is LinearLayoutManager) {
            throw IllegalStateException("StickyListHeadersDecoration can only be used with a " + "LinearLayoutManager.")
        }
    }
}
