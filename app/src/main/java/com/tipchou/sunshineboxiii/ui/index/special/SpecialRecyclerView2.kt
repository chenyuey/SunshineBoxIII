package com.tipchou.sunshineboxiii.ui.index.special

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.index.IndexActivity

/**
 * Created by 邵励治 on 2018/5/29.
 * Perfect Code
 */
class SpecialRecyclerView2(private val activity: IndexActivity) : RecyclerView.Adapter<SpecialRecyclerView2.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(layoutInflater.inflate(R.layout.fragment_special_item2, parent, false))

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
