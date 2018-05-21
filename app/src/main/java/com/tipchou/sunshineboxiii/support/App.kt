package com.tipchou.sunshineboxiii.support

import android.app.Application
import android.content.Context
import com.avos.avoscloud.AVOSCloud
import com.tencent.bugly.Bugly
import com.tipchou.sunshineboxiii.entity.local.MyObjectBox
import io.objectbox.BoxStore

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
class App : Application() {
    companion object {
        private var boxStore: BoxStore? = null
        fun getBoxStore() = boxStore

        var mAppContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        mAppContext = this
        boxStore = MyObjectBox.builder().androidContext(this@App).build()
        //Test Environment
//        AVOSCloud.initialize(this, "TDJUy79LG3JzSqVw7Hj1emIr-gzGzoHsz", "P1wJ6vfje0bVezeeio4eqCJ7")
        //Product Environment
        AVOSCloud.initialize(this, "CQBviH8f3TNrbRwzHfjTw7yk-gzGzoHsz", "5KnQsMhpWAAXYXvzbGV1YU62")
        Bugly.init(applicationContext, "37eaf578cd", false)
    }

}