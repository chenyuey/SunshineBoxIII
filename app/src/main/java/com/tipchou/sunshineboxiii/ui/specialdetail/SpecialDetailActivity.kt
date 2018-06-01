package com.tipchou.sunshineboxiii.ui.specialdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.SpecialSubjectLocal
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_special_detail.*

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
class SpecialDetailActivity : BaseActivity() {
    private var specialSubjectLocal: SpecialSubjectLocal? = null

    fun showSnackBar(message: String) {
        Snackbar.make(special_detail_act_coordinatorlayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun layoutId(): Int = R.layout.activity_special_detail

    @SuppressLint("SetTextI18n")
    override fun created(bundle: Bundle?) {
        setUpClickEvent()
        specialSubjectLocal = intent.getSerializableExtra(SPECIAL_SUBJECT_LOCAL) as SpecialSubjectLocal
        if (specialSubjectLocal == null) {
            showSnackBar("数据解析失败，请尝试刷新或清空数据库！")
        } else {
            special_detail_act_textview1.text = specialSubjectLocal?.title
            if (specialSubjectLocal?.describe != null && specialSubjectLocal?.describe != "") {
                special_detail_act_linearlayout1.visibility = View.VISIBLE
                special_detail_act_textview2.text = specialSubjectLocal?.describe
            }
            special_detail_act_recyclerview.layoutManager = GridLayoutManager(this, 4)
            special_detail_act_recyclerview.adapter = SpecialDetailRecyclerViewAdapter(this, specialSubjectLocal?.objectId!!)
        }
    }

    private fun setUpClickEvent() {
        special_detail_act_imagebutton1.setOnClickListener {
            finish()
        }
    }

    override fun resume() {
    }

    companion object {
        private const val SPECIAL_SUBJECT_LOCAL = "special subject local"
        fun newIntent(packageContext: Context, specialSubjectLocal: SpecialSubjectLocal): Intent {
            val intent = Intent(packageContext, SpecialDetailActivity::class.java)
            intent.putExtra(SPECIAL_SUBJECT_LOCAL, specialSubjectLocal)
            return intent
        }
    }
}
