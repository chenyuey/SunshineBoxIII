package com.tipchou.sunshineboxiii.support

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.tipchou.sunshineboxiii.pojo.localpojo.MyObjectBox
import io.objectbox.BoxStore

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
class App : Application() {
    companion object {
        private var boxStore: BoxStore? = null
        fun getBoxStore() = boxStore
    }

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this@App).build()

        AVOSCloud.initialize(this, "TDJUy79LG3JzSqVw7Hj1emIr-gzGzoHsz", "P1wJ6vfje0bVezeeio4eqCJ7")
    }
}