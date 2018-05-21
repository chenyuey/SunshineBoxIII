package com.tipchou.sunshineboxiii.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

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
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(layoutId())
        supportActionBar?.hide()
        created(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        resume()
    }

}