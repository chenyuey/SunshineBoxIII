package com.tipchou.sunshineboxiii.ui.main

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
            main_act_textview1.text = "\"${it?.status.toString()} : ${it?.data?.userName} message: ${it?.message}\""
            Log.e("FUCK", "\"${it?.status.toString()} : ${it?.data?.userName} message: ${it?.message}\"")
        })

        main_act_textview1.setOnClickListener {
            Log.e("Fuck", "onClick")
            viewModel?.loadUser()
        }
    }

    override fun resume() {
    }
}