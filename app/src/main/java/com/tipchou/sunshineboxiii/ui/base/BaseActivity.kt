package com.tipchou.sunshineboxiii.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * Created by 邵励治 on 2018/3/26.
 * Perfect Code
 */
abstract class BaseActivity : AppCompatActivity() {
    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun created(bundle: Bundle?)

    protected abstract fun resume()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        supportActionBar?.hide()
        created(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        resume()
    }
}