package com.tipchou.sunshineboxiii.ui.index.special

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseFragment
import com.tipchou.sunshineboxiii.ui.index.IndexActivity
import kotlinx.android.synthetic.main.fragment_special.*

/**
 * Created by 邵励治 on 2018/5/29.
 * Perfect Code
 */
class SpecialFragment : BaseFragment() {
    override fun layoutId(): Int = R.layout.fragment_special

    override fun created(bundle: Bundle?) {
        val adapter1 = SpecialRecyclerView1(activity as IndexActivity, this)
        val adapter2 = SpecialRecyclerView2(activity as IndexActivity, this)
        val layoutManager1 = LinearLayoutManager(activity)
        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        special_fgm_recyclerview1.layoutManager = layoutManager1
        special_fgm_recyclerview1.adapter = adapter1
        special_fgm_recyclerview2.layoutManager = layoutManager2
        special_fgm_recyclerview2.adapter = adapter2
    }

    override fun resumed() {
    }
}
