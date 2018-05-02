package com.tipchou.sunshineboxiii.ui.index

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_index.*
import kotlinx.android.synthetic.main.activity_index_content.*

/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
class IndexActivity : BaseActivity() {

    private var refreshAnimator: ObjectAnimator? = null

    private fun setUpClickEvent() {
        //drawer layout
        index_act_imagebuttton1.setOnClickListener {
            val drawerLockMode = index_act_drawerlayout.getDrawerLockMode(GravityCompat.START)
            if (index_act_drawerlayout.isDrawerVisible(GravityCompat.START) && drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN) {
                index_act_drawerlayout.closeDrawer(GravityCompat.START)
            } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
                index_act_drawerlayout.openDrawer(GravityCompat.START)
            }
        }
        //subject button
        index_act_linearlayout1.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview1.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview1.background = getDrawable(R.drawable.ic_nursery_orange)
        }
        index_act_linearlayout2.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview2.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview2.background = getDrawable(R.drawable.ic_music_orange)
        }
        index_act_linearlayout3.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview3.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview3.background = getDrawable(R.drawable.ic_reading_orange)
        }
        index_act_linearlayout4.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview4.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview4.background = getDrawable(R.drawable.ic_game_orange)
        }
        //tag button
        index_act_linearlayout5.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview5.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview5.background = getDrawable(R.drawable.ic_health_orange)
        }
        index_act_linearlayout6.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview6.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview6.background = getDrawable(R.drawable.ic_language_orange)
        }
        index_act_linearlayout7.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview7.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview7.background = getDrawable(R.drawable.ic_social_orange)
        }
        index_act_linearlayout8.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview8.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview8.background = getDrawable(R.drawable.ic_science_orange)
        }
        index_act_linearlayout9.setOnClickListener {
            makeEveryBodyGray()
            index_act_textview9.setTextColor(Color.parseColor("#f09038"))
            index_act_imageview9.background = getDrawable(R.drawable.ic_art_orange)
        }
        //refresh button
        index_act_view1.setOnClickListener {
        }
    }

    private fun setUpAnimator() {
        //drawer layout animator
        index_act_drawerlayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val angle = 720 * slideOffset
                index_act_imagebuttton1.rotation = angle
            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
        //refresh layout animator
        refreshAnimator = getRefreshButtonAnimator(index_act_imageview10)
    }

    override fun layoutId(): Int = R.layout.activity_index

    override fun created(bundle: Bundle?) {
        setUpClickEvent()
        setUpAnimator()
    }

    override fun resume() {

    }

    private fun makeEveryBodyGray() {
        if (index_act_textview1.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview1.setTextColor(Color.parseColor("#666666"))
            index_act_imageview1.background = getDrawable(R.drawable.ic_nursery_black)
        }

        if (index_act_textview2.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview2.setTextColor(Color.parseColor("#666666"))
            index_act_imageview2.background = getDrawable(R.drawable.ic_music_black)
        }

        if (index_act_textview3.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview3.setTextColor(Color.parseColor("#666666"))
            index_act_imageview3.background = getDrawable(R.drawable.ic_reading_black)
        }

        if (index_act_textview4.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview4.setTextColor(Color.parseColor("#666666"))
            index_act_imageview4.background = getDrawable(R.drawable.ic_game_black)
        }

        if (index_act_textview5.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview5.setTextColor(Color.parseColor("#666666"))
            index_act_imageview5.background = getDrawable(R.drawable.ic_health_black)
        }

        if (index_act_textview6.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview6.setTextColor(Color.parseColor("#666666"))
            index_act_imageview6.background = getDrawable(R.drawable.ic_language_black)
        }

        if (index_act_textview7.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview7.setTextColor(Color.parseColor("#666666"))
            index_act_imageview7.background = getDrawable(R.drawable.ic_social_black)
        }

        if (index_act_textview8.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview8.setTextColor(Color.parseColor("#666666"))
            index_act_imageview8.background = getDrawable(R.drawable.ic_science_black)
        }

        if (index_act_textview9.currentTextColor == Color.parseColor("#f09038")) {
            index_act_textview9.setTextColor(Color.parseColor("#666666"))
            index_act_imageview9.background = getDrawable(R.drawable.ic_art_black)
        }
    }

    private fun getRefreshButtonAnimator(imageView: ImageView): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat<View>(imageView, View.ROTATION, 0F, 360F)
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.duration = 1000
        objectAnimator.interpolator = LinearInterpolator()
        return objectAnimator
    }

    private fun startRefresh() {
        index_act_view1.isEnabled = false
        index_act_textview11.setTextColor(Color.parseColor("#999999"))
        index_act_imageview10.background = getDrawable(R.drawable.refresh_69)
        startRotatingRefreshButton()
    }

    private fun stopRefresh() {
        index_act_view1.isEnabled = true
        index_act_textview11.setTextColor(Color.parseColor("#333333"))
        index_act_imageview10.background = getDrawable(R.drawable.refresh)
        stopRotatingRefreshButton()
    }

    private fun startRotatingRefreshButton() {
        val animator = refreshAnimator
        if (animator == null) {
            //should not be here
            Log.e(localClassName, "startRotating():animator==null")
        } else {
            if (animator.isPaused) {
                animator.resume()
            } else {
                animator.start()
            }
        }
    }

    private fun stopRotatingRefreshButton() {
        val animator = refreshAnimator
        if (animator == null) {
            //should not be here
            Log.e(localClassName, "stopRotating():animator==null")
        } else {
            animator.pause()
        }
    }
}
