package com.tipchou.sunshineboxiii.ui.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity

/**
 * Created by 邵励治 on 2018/5/24.
 * Perfect Code
 */
class FavoriteActivity : BaseActivity() {
    private var favoriteViewModel: FavoriteViewModel? = null

    init {
    }

    override fun layoutId(): Int = R.layout.activity_favorite

    override fun created(bundle: Bundle?) {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        favoriteViewModel?.getFavorite()?.observe(this, Observer {
            favoriteViewModel?.loadFavoriteLesson()
        })
        favoriteViewModel?.getFavoriteLesson()?.observe(this, Observer {
            Log.e("FUCK", it?.data?.size.toString())
        })
    }

    override fun resume() {
    }
}
