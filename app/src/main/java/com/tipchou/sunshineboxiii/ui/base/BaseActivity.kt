package com.tipchou.sunshineboxiii.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.avos.avoscloud.AVAnalytics
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context.ACTIVITY_SERVICE
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.util.Log
import com.tipchou.sunshineboxiii.support.App


/**
 * Created by 邵励治 on 2018/3/26.
 * Perfect Code
 */
abstract class BaseActivity : AppCompatActivity() {
//    private var resumeTime: Long? = null
    private var resumeTime: Long? = App.resumeTime
    private var pauseTime: Long? = null
//    private val boxStore: BoxStore? by lazy { App.getBoxStore() }

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
        resumeTime = App.resumeTime;
        if (resumeTime == null)
        {
            resumeTime = System.currentTimeMillis()
            App.resumeTime = resumeTime
        }
        AVAnalytics.onResume(this)
        Log.e("isAppOnForeground","YES================"+resumeTime);
        resume()
    }

    override fun onPause() {
        super.onPause()
        AVAnalytics.onPause(this)
    }

    override fun onStop() {
        // TODO Auto-generated method stub
        super.onStop()

        if (!isAppOnForeground()) {
            // app 进入后台
            // 全局变量isActive = false 记录当前已经进入后台
            pauseTime = System.currentTimeMillis()
            Log.e("isAppOnForeground","NO================"+pauseTime);
            if (resumeTime != null) {
                if (AVUser.getCurrentUser() != null) {
                    val accessRecord = AVObject("UserAction")
                    accessRecord.put("userId", AVUser.getCurrentUser().objectId)
                    accessRecord.put("usageTime", (pauseTime!! - resumeTime!!).toString())
                    accessRecord.put("behaviorType", "useApp")
                    accessRecord.put("equipment", "androidApp")
                    accessRecord.saveInBackground()
                    App.resumeTime = null;
                }
            }
            AVAnalytics.onPause(this)
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    fun isAppOnForeground(): Boolean {
        // Returns a list of application processes that are running on the
        // device

        val activityManager = applicationContext
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageName = applicationContext.packageName
        val appProcesses = activityManager
                .runningAppProcesses ?: return false
        for (appProcess in appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName == packageName && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }

        return false
    }

}