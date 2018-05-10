package com.tipchou.sunshineboxiii.ui.index

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.support.LessonType
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_index.*
import kotlinx.android.synthetic.main.activity_index_content.*
import java.io.File


/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
class IndexActivity : BaseActivity() {
    private var viewModel: IndexViewModel? = null

    private var refreshAnimator: ObjectAnimator? = null

    //-------------------------------onCreate Method------------------------------------------------
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
            viewModel?.setLessonType(LessonType.NURSERY)
            viewModel?.loadLesson()
        }
        index_act_linearlayout2.setOnClickListener {
            viewModel?.setLessonType(LessonType.MUSIC)
            viewModel?.loadLesson()
        }
        index_act_linearlayout3.setOnClickListener {
            viewModel?.setLessonType(LessonType.READING)
            viewModel?.loadLesson()
        }
        index_act_linearlayout4.setOnClickListener {
            viewModel?.setLessonType(LessonType.GAME)
            viewModel?.loadLesson()
        }
        //tag button
        index_act_linearlayout5.setOnClickListener {
            viewModel?.setLessonType(LessonType.HEALTH)
            viewModel?.loadLesson()
        }
        index_act_linearlayout6.setOnClickListener {
            viewModel?.setLessonType(LessonType.LANGUAGE)
            viewModel?.loadLesson()
        }
        index_act_linearlayout7.setOnClickListener {
            viewModel?.setLessonType(LessonType.SOCIAL)
            viewModel?.loadLesson()
        }
        index_act_linearlayout8.setOnClickListener {
            viewModel?.setLessonType(LessonType.SCIENCE)
            viewModel?.loadLesson()
        }
        index_act_linearlayout9.setOnClickListener {
            viewModel?.setLessonType(LessonType.ART)
            viewModel?.loadLesson()
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

    private fun setUpNetWorkChangeBroadcast() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val netWorkChangeBroadcast = NetworkChangeBroadcast()
        registerReceiver(netWorkChangeBroadcast, intentFilter)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        viewModel?.getNetStatus()?.observe(this, Observer {
            if (it == null) {
                //should not be here
            } else {
                if (it) {
                    index_act_textview10.text = "阳光盒子"
                } else {
                    index_act_textview10.text = "阳光盒子（离线模式）"
                }
            }
        })
        viewModel?.getLessonType()?.observe(this, Observer {
            when (it) {
                LessonType.NURSERY -> {
                    makeEveryBodyGray()
                    index_act_textview1.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview1.background = getDrawable(R.drawable.ic_nursery_orange)
                }
                LessonType.MUSIC -> {
                    makeEveryBodyGray()
                    index_act_textview2.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview2.background = getDrawable(R.drawable.ic_music_orange)

                }
                LessonType.READING -> {
                    makeEveryBodyGray()
                    index_act_textview3.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview3.background = getDrawable(R.drawable.ic_reading_orange)

                }
                LessonType.GAME -> {
                    makeEveryBodyGray()
                    index_act_textview4.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview4.background = getDrawable(R.drawable.ic_game_orange)

                }
                LessonType.HEALTH -> {
                    makeEveryBodyGray()
                    index_act_textview5.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview5.background = getDrawable(R.drawable.ic_health_orange)
                }
                LessonType.LANGUAGE -> {
                    makeEveryBodyGray()
                    index_act_textview6.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview6.background = getDrawable(R.drawable.ic_language_orange)
                }
                LessonType.SOCIAL -> {
                    makeEveryBodyGray()
                    index_act_textview7.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview7.background = getDrawable(R.drawable.ic_social_orange)

                }
                LessonType.SCIENCE -> {
                    makeEveryBodyGray()
                    index_act_textview8.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview8.background = getDrawable(R.drawable.ic_science_orange)
                }
                LessonType.ART -> {
                    makeEveryBodyGray()
                    index_act_textview9.setTextColor(Color.parseColor("#f09038"))
                    index_act_imageview9.background = getDrawable(R.drawable.ic_art_orange)

                }
            }
        })
        viewModel?.getRole()?.observe(this, Observer {
            Log.e("Role", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
            if (it == null) {
                //should not be here
            } else {
                val roleList = it.data
                if (roleList == null) {
                    //should not be here!!!
                } else {
                    val isEditor = isUserEditor(roleList)
                    if (isEditor) {
                        index_act_textview10.text = "阳光盒子（审阅模式）"
                    } else {
                        index_act_textview10.text = "阳光盒子"
                    }
                }
            }
        })
        viewModel?.getLesson()?.observe(this, Observer {
            Log.e("Lesson", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
        })
    }

    private fun checkPermissions(): Boolean {
        ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if (permission) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
        return !permission
    }

    private fun createRootFolder(): File {
        val file = File(Environment.getExternalStorageDirectory().path + File.separator + "SunshineBox_III")
        if (!file.exists()) {
            Log.i("Create Folder: ", file.mkdirs().toString())
        }
        return file
    }

    //--------------------------------Main Method---------------------------------------------------
    override fun layoutId(): Int = R.layout.activity_index

    override fun created(bundle: Bundle?) {
        setUpClickEvent()
        setUpAnimator()
        setUpNetWorkChangeBroadcast()
        setUpViewModel()
        setUpRecyclerView()
        checkPermissions()
        createRootFolder()
    }

    private fun setUpRecyclerView() {
        val adapter = IndexRecyclerViewAdapter(this)
        index_act_recyclerview.layoutManager = GridLayoutManager(this, 4)
        index_act_recyclerview.adapter = adapter
    }

    override fun resume() {
        viewModel?.setNetStatus(getNetworkState(this))
        viewModel?.loadRole()
        viewModel?.loadLesson()
    }

    //--------------------------------Snack Bar-----------------------------------------------------
    private fun showSnackBar(message: String) {
        Snackbar.make(index_act_coordinatorlayout, message, Snackbar.LENGTH_LONG).show()
    }

    //--------------------------------Role----------------------------------------------------------
    private fun isUserEditor(roleList: List<RoleLocal>): Boolean {
        var isEditor = false
        for (role in roleList) {
            when (role.name) {
                "admin" -> isEditor = true
                "admin1" -> isEditor = true
                "admin2" -> isEditor = true
            }
        }
        return isEditor
    }

    //--------------------------------Network Status------------------------------------------------
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?
        activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    private inner class NetworkChangeBroadcast : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success: Boolean = isConnected(this@IndexActivity)
            viewModel?.setNetStatus(success)
        }
    }

    private fun getNetworkState(context: Context): Boolean {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取NetworkInfo对象
        val allNetworkInfo = connectivityManager.allNetworkInfo
        // 遍历每一个对象
        for (networkInfo in allNetworkInfo) {
            if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                // 网络状态可用
                return true
            }
        }
        // 没有可用的网络
        return false
    }

    //---------------------------------Subject/Tag Button-------------------------------------------
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

    //---------------------------------Refresh Button-----------------------------------------------
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
