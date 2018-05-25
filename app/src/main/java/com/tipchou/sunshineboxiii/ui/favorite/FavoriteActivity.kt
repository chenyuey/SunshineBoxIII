package com.tipchou.sunshineboxiii.ui.favorite

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_favorite.*

/**
 * Created by 邵励治 on 2018/5/24.
 * Perfect Code
 */
class FavoriteActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_favorite

    override fun created(bundle: Bundle?) {
        favorite_act_imagebutton1.setOnClickListener {
            finish()
        }
        favorite_act_recyclerview.layoutManager = GridLayoutManager(this, 4)
        favorite_act_recyclerview.adapter = FavoriteRecyclerViewAdapter(this)
    }

    override fun resume() {
    }

    fun showSnackBar(message: String) {
        Snackbar.make(favorite_act_coordinatorlayout, message, Snackbar.LENGTH_LONG).show()
    }
}
