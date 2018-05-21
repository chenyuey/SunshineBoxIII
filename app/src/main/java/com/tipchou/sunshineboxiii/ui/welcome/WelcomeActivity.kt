package com.tipchou.sunshineboxiii.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.support.ActivationActivityManager
import com.tipchou.sunshineboxiii.ui.phone_number_verify.PhoneNumberVerifyActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : ActivationActivityManager() {
    override fun layoutId(): Int = R.layout.activity_welcome

    override fun created(bundle: Bundle?) {
        welcome_button.setOnClickListener {
            startActivity(Intent(this, PhoneNumberVerifyActivity::class.java))
        }
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, WelcomeActivity::class.java)
        }
    }

    override fun resume() {
    }
}
