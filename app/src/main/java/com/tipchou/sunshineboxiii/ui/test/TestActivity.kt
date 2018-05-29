package com.tipchou.sunshineboxiii.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import com.tipchou.sunshineboxiii.ui.index.lesson.LessonFragment
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_test

    private class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LessonFragment()
                else -> {
                    throw Exception()
                }
            }
        }

        override fun getCount(): Int = 1
    }

    @SuppressLint("SetTextI18n")
    override fun created(bundle: Bundle?) {
        test_act_viewpager.adapter = MyAdapter(supportFragmentManager)
//        viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)
//
//        viewModel?.getUser()?.observe(this, Observer {
//            Log.e("Fuck", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
//            if (it == null) {
//
//            } else {
//                if (it.data == null) {
//
//                } else {
//                    main_act_textview1.text = ""
//                    for (item in it.data) {
//                        main_act_textview1.text = "${main_act_textview1.text}\n${item.userName}"
//                        Log.e("Item", "${item.tags}")
//                    }
//                }
//            }
//        })
//
//        main_act_textview1.setOnClickListener {
//            Log.e("Fuck", "onClick")
//            viewModel?.loadUser()
//        }

//        Log.e("before", System.currentTimeMillis().toString())
//        val list = DbDao().getLesson(Arrays.asList("5b02943e1b69e600686b02c1", "5b03b2209f5454477c35d725", "5abde607a22b9d004342a5ee", "5abc584eee920a00475f3dbb", "5ae018a10b6160299ae6a84f"))
//        Log.e("FUCK", list.size.toString())
//        Log.e("after", System.currentTimeMillis().toString())

//        WebDao().getLesson(Arrays.asList())

//        val parameters = HashMap<String, ArrayList<FavoriteActionLocal>>()
//        val list = arrayListOf<FavoriteActionLocal>()
//        list.add(FavoriteActionLocal("5a9e4af4fe88c21c80cd6b5f", System.currentTimeMillis(), false))
//        parameters["collectionActionArr"] = list
//
//        AVCloud.callFunctionInBackground("collection", parameters, object : FunctionCallback<HashMap<String, String>>() {
//            override fun done(p0: HashMap<String, String>?, p1: AVException?) {
//                Log.e("云函数调用成功", "success")
//            }
//        })
    }


    override fun resume() {
    }


}
