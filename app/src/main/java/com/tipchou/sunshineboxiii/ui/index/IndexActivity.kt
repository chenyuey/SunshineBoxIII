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
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.avos.avoscloud.AVUser
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.support.Resource
import com.tipchou.sunshineboxiii.ui.StartActivity
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import com.tipchou.sunshineboxiii.ui.favorite.FavoriteActivity
import com.tipchou.sunshineboxiii.ui.guide.GuideActivity
import com.tipchou.sunshineboxiii.ui.index.lesson.LessonFragment
import com.tipchou.sunshineboxiii.ui.index.lesson.LessonViewModel
import com.tipchou.sunshineboxiii.ui.index.special.SpecialFragment
import com.tipchou.sunshineboxiii.ui.index.special.SpecialViewModel
import kotlinx.android.synthetic.main.activity_index.*
import kotlinx.android.synthetic.main.activity_index_content.*
import java.io.File


/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
class IndexActivity : BaseActivity() {
    private var lessonViewModel: LessonViewModel? = null

    private var specialViewModel: SpecialViewModel? = null

    private var refreshAnimator: ObjectAnimator? = null

    private var netWorkChangeBroadcast: BroadcastReceiver? = null

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
        //refresh button
        index_act_view1.setOnClickListener {
            lessonViewModel?.loadLesson()
            specialViewModel?.loadSpecialSubject()
        }
        //侧边菜单
        val headView: View = index_act_navigationview.inflateHeaderView(R.layout.index_act_headerlayout)
        val resetTextView = headView.findViewById<TextView>(R.id.index_drawer_textview1)
        val signOutTextView = headView.findViewById<TextView>(R.id.index_drawer_textview2)
        val favoriteLinearLayout = headView.findViewById<LinearLayout>(R.id.index_drawer_linearlayout1)
        val cellphoneTextView = headView.findViewById<TextView>(R.id.index_drawer_textview3)
        cellphoneTextView.text = AVUser.getCurrentUser().mobilePhoneNumber
        resetTextView.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("确定重置软件")
            alertDialogBuilder.setMessage("这会删除全部的本地文件")
            alertDialogBuilder.setPositiveButton("确定") { _, _ ->
                lessonViewModel?.clearDatabase()
            }
            alertDialogBuilder.setNegativeButton("取消") { _, _ ->

            }
            alertDialogBuilder.create().show()
        }
        signOutTextView.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("确定登出")
            alertDialogBuilder.setMessage("这会退出当前账号，并丢失全部的本地文件")
            alertDialogBuilder.setPositiveButton("确定") { _, _ ->
                lessonViewModel?.clearDatabase()
                AVUser.logOut()
                startActivity(StartActivity.newIntent(this))
                finish()
            }
            alertDialogBuilder.setNegativeButton("取消") { _, _ ->

            }
            alertDialogBuilder.create().show()
        }
        favoriteLinearLayout.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
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
        netWorkChangeBroadcast = NetworkChangeBroadcast()
        registerReceiver(netWorkChangeBroadcast, intentFilter)
    }

    private fun setUpViewModel() {
        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)
        lessonViewModel?.getRole()?.observe(this, Observer {
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
        lessonViewModel?.getLesson()?.observe(this, Observer {
            Log.e("Lesson", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
            when (it?.status) {
                Resource.Status.SUCCESS -> stopRefresh()
                Resource.Status.ERROR -> stopRefresh()
                Resource.Status.LOADING -> startRefresh()
            }
        })

        specialViewModel = ViewModelProviders.of(this).get(SpecialViewModel::class.java)
        specialViewModel?.getSpecialSubject()?.observe(this, Observer {
            when (it?.status) {
                Resource.Status.SUCCESS -> stopRefresh()
                Resource.Status.ERROR -> stopRefresh()
                Resource.Status.LOADING -> startRefresh()
            }
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
        val file1 = File(Environment.getExternalStorageDirectory().path + File.separator + "Sunshinebox_III" + File.separator + "normal")
        if (!file1.exists()) {
            Log.i("Create Folder", file1.mkdirs().toString())
        }
        val file2 = File(Environment.getExternalStorageDirectory().path + File.separator + "Sunshinebox_III" + File.separator + "editor")
        if (!file2.exists()) {
            Log.i("Create Folder", file2.mkdirs().toString())
        }
        return file
    }

    //--------------------------------Main Method---------------------------------------------------
    override fun layoutId(): Int = R.layout.activity_index

    override fun created(bundle: Bundle?) {
        setUpUserGuide()
        setUpClickEvent()
        setUpAnimator()
        setUpNetWorkChangeBroadcast()
        setUpViewModel()
        checkPermissions()
        createRootFolder()
        index_act_viewpager.adapter = MyAdapter(supportFragmentManager)
        index_act_tablayout.setupWithViewPager(index_act_viewpager)
    }

    private fun setUpUserGuide() {
        val userGuideCache = getSharedPreferences("UserGuide", Context.MODE_PRIVATE)
        val firstOpenIndex = userGuideCache.getBoolean("FirstOpenIndex", true)
        if (firstOpenIndex) {
            startActivity(GuideActivity.newIntent(this, GuideActivity.Companion.ActivityName.Index))
            val editor = userGuideCache.edit()
            editor.putBoolean("FirstOpenIndex", false)
            editor.apply()
        }
    }

    private class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LessonFragment()
                else -> SpecialFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> {
                    "全部课程"
                }
                else -> {
                    "专题"
                }
            }
        }

        override fun getCount(): Int = 2
    }

    override fun resume() {
        lessonViewModel?.setNetStatus(getNetworkState(this))
        lessonViewModel?.loadRole()
        lessonViewModel?.loadLesson()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(netWorkChangeBroadcast)
    }

    //--------------------------------Snack Bar-----------------------------------------------------
    fun showSnackBar(message: String) {
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
            lessonViewModel?.setNetStatus(success)
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

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, IndexActivity::class.java)
        }
    }
}
