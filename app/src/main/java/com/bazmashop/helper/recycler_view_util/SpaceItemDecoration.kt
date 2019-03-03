package com.clearcommon.helper.recycler_view_util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceItemDecoration(private val space: Int, private val internalSpace: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state!!)

        val pos = parent.getChildAdapterPosition(view)
        val size = state!!.itemCount
        if (pos == RecyclerView.NO_POSITION)
            return


        if (pos == 0)
            outRect.set(space, 0, 0, 0)
        else if (size > 0 && pos == size - 1)
            outRect.set(internalSpace, 0, space, 0)
        else
            outRect.set(internalSpace, 0, 0, 0)
    }
}