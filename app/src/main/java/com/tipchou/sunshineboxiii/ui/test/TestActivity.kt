package com.tipchou.sunshineboxiii.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*
import uk.co.deanwild.materialshowcaseview.IShowcaseListener
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

class TestActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_test

    @SuppressLint("SetTextI18n")
    override fun created(bundle: Bundle?) {
        test_act_button1.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(this, "1")
            MaterialShowcaseView.Builder(this)
                    .setTarget(test_act_button1)
                    .setDismissText("继续")
                    .setContentText("点击课程下载课程内容，下载后没有网络也可使用")
                    .setTitleText("欢迎使用阳光盒子")
                    .singleUse("1")
                    .setDelay(0)
                    .setListener(object : IShowcaseListener {
                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {
                            Log.e("button1", "displayed")
                        }

                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                            MaterialShowcaseView.resetSingleUse(this@TestActivity, "2")
                            MaterialShowcaseView.Builder(this@TestActivity)
                                    .setTarget(test_act_button2)
                                    .setDismissText("继续")
                                    .setContentText("下载完成，点击图片进入课程")
                                    .singleUse("2")
                                    .setDelay(0)
                                    .show()
                        }

                    })
                    .show()

        }

        test_act_button2.setOnClickListener {

        }

        test_act_button3.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(this, "3")
            MaterialShowcaseView.Builder(this)
                    .setTarget(test_act_button3)
                    .setDismissText("继续")
                    .setContentText("点击科目和领域可以筛选课程")
                    .setTitleText("按科目和领域找课程")
                    .singleUse("3")
                    .setDelay(0)
                    .show()
        }

        test_act_button4.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(this, "4")
            MaterialShowcaseView.Builder(this)
                    .setTarget(test_act_button4)
                    .setDismissText("继续")
                    .setContentText("按页面左侧的教案来备课，课堂中使用的素材在右侧")
                    .setTitleText("我们帮助你丰富你的课堂")
                    .singleUse("4")
                    .setDelay(0)
                    .show()
            test_act_button5.setOnClickListener {
                MaterialShowcaseView.resetSingleUse(this, "5")
                MaterialShowcaseView.Builder(this)
                        .setTarget(test_act_button5)
                        .setDismissText("继续")
                        .setContentText("点击小心心收藏课程")
                        .setTitleText("这课很好先记下来")
                        .singleUse("5")
                        .setDelay(0)
                        .show()

            }
        }
    }


    override fun resume() {

    }
}
