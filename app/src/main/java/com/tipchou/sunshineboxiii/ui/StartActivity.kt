package com.tipchou.sunshineboxiii.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.avos.avoscloud.AVAnalytics
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.support.ActivationActivityManager
import com.tipchou.sunshineboxiii.ui.index.IndexActivity
import com.tipchou.sunshineboxiii.ui.welcome.WelcomeActivity

class StartActivity : ActivationActivityManager() {
    override fun layoutId(): Int = R.layout.activity_start

    override fun created(bundle: Bundle?) {
        if (AVUser.getCurrentUser() != null) {
            AVAnalytics.onEvent(this, "用户打开应用总数", AVUser.getCurrentUser().username)
            val accessRecord = AVObject("UserAction")
            accessRecord.put("userId", AVUser.getCurrentUser().objectId)
            accessRecord.put("behaviorType", "openApp")
            accessRecord.put("equipment", "androidApp")
            accessRecord.saveInBackground()
            startActivity(IndexActivity.newIntent(this))
            finish()
        } else {
            startActivity(WelcomeActivity.newIntent(this))
            finish()
        }
    }

    override fun resume() {
    }

    companion object {
        fun newIntent(packageContext: Context): Intent = Intent(packageContext, StartActivity::class.java)
    }
}
