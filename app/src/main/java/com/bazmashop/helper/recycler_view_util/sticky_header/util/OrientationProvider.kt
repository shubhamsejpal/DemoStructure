package com.clearcommon.helper.recycler_view_util.sticky_header.util

import android.support.v7.widget.RecyclerView

/**
 * Interface for getting the orientation of a RecyclerView from its LayoutManager
 */
interface OrientationProvider {

    fun getOrientation(recyclerView: RecyclerView): Int

    fun isReverseLayout(recyclerView: RecyclerView): Boolean
}
