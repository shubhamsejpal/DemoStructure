package com.clearcommon.helper.recycler_view_util.sticky_header.caching

import android.support.v4.util.LongSparseArray
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.clearcommon.helper.recycler_view_util.sticky_header.StickyRecyclerHeadersAdapter
import com.clearcommon.helper.recycler_view_util.sticky_header.util.OrientationProvider

/**
 * An implementation of [HeaderProvider] that creates and caches header views
 */
class HeaderViewCache(
    private val mAdapter: StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>,
    private val mOrientationProvider: OrientationProvider
) : HeaderProvider {
    private val mHeaderViews = LongSparseArray<View>()

    override fun getHeader(parent: RecyclerView, position: Int): View {
        val headerId = mAdapter.getHeaderId(position)

        var header: View? = mHeaderViews.get(headerId)
        if (header == null) {
            //TODO - recycle views
            val viewHolder = mAdapter.onCreateHeaderViewHolder(parent)
            mAdapter.onBindHeaderViewHolder(viewHolder, position)
            header = viewHolder.itemView
            if (header!!.layoutParams == null) {
                header.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            val widthSpec: Int
            val heightSpec: Int

            if (mOrientationProvider.getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
            } else {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.UNSPECIFIED)
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)
            }

            val childWidth = ViewGroup.getChildMeasureSpec(
                widthSpec,
                parent.paddingLeft + parent.paddingRight, header.layoutParams.width
            )
            val childHeight = ViewGroup.getChildMeasureSpec(
                heightSpec,
                parent.paddingTop + parent.paddingBottom, header.layoutParams.height
            )
            header.measure(childWidth, childHeight)
            header.layout(0, 0, header.measuredWidth, header.measuredHeight)
            mHeaderViews.put(headerId, header)
        }
        return header
    }

    override fun invalidate() {
        mHeaderViews.clear()
    }
}
