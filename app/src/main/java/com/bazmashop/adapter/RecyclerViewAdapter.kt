package com.bazmashop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bazmashop.R
import com.bazmashop.dashboard.view.ShopFragment
import com.bazmashop.model.ResponseItemListModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.row_items.view.*

/**
 * Created by shubhamsejpal on 23/01/19.
 */

class RecyclerViewAdapter(private val mContext: Context, private val mDataSet: ArrayList<ResponseItemListModel.Data.Product>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ivFav: ImageView = v.ivFav
        var ivItemImage: ImageView = v.ivItemImage
        var tvName: TextView = v.tvName
        var tvBrandName: TextView = v.tvBrandName
        var tvPrice: TextView = v.tvPrice

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.row_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mDataSet[position].image.isNotEmpty())
            Glide.with(mContext).load(mDataSet[position].image).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_noimg).into(holder.ivItemImage)
        if (mDataSet[position].name.isNotEmpty())
            holder.tvName.text = mDataSet[position].name
        if (mDataSet[position].brandName.isNotEmpty())
            holder.tvBrandName.text = mDataSet[position].brandName
        if (mDataSet[position].currencyCode.isNotEmpty() && mDataSet[position].finalPrice.isNotEmpty())
            holder.tvPrice.text = mDataSet[position].currencyCode + " " + mDataSet[position].finalPrice
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }
}
