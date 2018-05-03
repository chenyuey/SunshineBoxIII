package com.tipchou.sunshineboxiii.ui.test

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.tipchou.sunshineboxiii.R

import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {
    private var viewModel: TestViewModel? = null

    override fun layoutId(): Int = R.layout.activity_test

    @SuppressLint("SetTextI18n")
    override fun created(bundle: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)

        viewModel?.getUser()?.observe(this, Observer {
            Log.e("Fuck", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
            if (it == null) {

            } else {
                if (it.data == null) {

                } else {
                    main_act_textview1.text = ""
                    for (item in it.data) {
                        main_act_textview1.text = "${main_act_textview1.text}\n${item.userName}"
                    }
                }
            }
        })

        main_act_textview1.setOnClickListener {
            Log.e("Fuck", "onClick")
            viewModel?.loadUser()
        }
    }

    override fun resume() {
    }
}
