package com.tipchou.sunshineboxiii.ui.test

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.avos.avoscloud.*
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.R.id.main_act_textview1
import com.tipchou.sunshineboxiii.support.LessonType
import com.tipchou.sunshineboxiii.support.dao.WebDao

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
                        Log.e("Item", "${item.tags}")
                    }
                }
            }
        })

        main_act_textview1.setOnClickListener {
            Log.e("Fuck", "onClick")
            viewModel?.loadUser()
        }

        WebDao().getLesson(LessonType.HEALTH)
//        Log.e("在登录前获取用户", AVUser.getCurrentUser().username)

//        AVUser.logInInBackground("shaolizhi", "12345678", object : LogInCallback<AVUser>() {
//            override fun done(p0: AVUser?, p1: AVException?) {
//                if (p1 == null) {
//                    //网络请求成功
//                    Log.e("FUCK", "登录成功!")
//                    if (p0 == null) {
//                        //should not be here
//                        Log.e("FUCK", "should not be here")
//                    } else {
//                        Log.e("FUCK", "当前用户为: ${p0.username}")
//                    }
//                } else {
//                    Log.e("FUCK","登录失败")
//                }
//            }
//        })


//        Log.e("在登录后获取用户", AVUser.getCurrentUser().username)
//        AVUser.getCurrentUser().getRolesInBackground(object : AVCallback<List<AVRole>>() {
//            override fun internalDone0(p0: List<AVRole>?, p1: AVException?) {
//                if (p1 == null) {
//                    Log.d("FUCK", "网络请求成功")
//                    if (p0 == null) {
//                        //should not be here!!!
//                        Log.d("FUCK", "should not be here!!!")
//                    } else {
//                        Log.d("用户角色", p0.toString())
//                    }
//                } else {
//                    Log.d("FUCK", "网络请求失败")
//                }
//            }
//        })
    }

    override fun resume() {
    }
}
