package com.tipchou.sunshineboxiii.ui.guide

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_guide.*
import uk.co.deanwild.materialshowcaseview.IShowcaseListener
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView


class GuideActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_guide

    override fun created(bundle: Bundle?) {
        guide_act_view1.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(this, "1")
            MaterialShowcaseView.Builder(this)
                    .setTarget(guide_act_view1)
                    .setDismissText("「点击这里继续」")
                    .setContentText("点击课程下载课程内容，下载后没有网络也可使用")
                    .setTitleText("欢迎使用阳光盒子")
                    .setDismissStyle(Typeface.SANS_SERIF)
                    .singleUse("1")
                    .setDelay(0)
                    .setListener(object : IShowcaseListener {
                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {
                        }

                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                            guide_act_constraintLayout.setBackgroundResource(R.drawable.guide_image2)
                            MaterialShowcaseView.resetSingleUse(this@GuideActivity, "2")
                            MaterialShowcaseView.Builder(this@GuideActivity)
                                    .setTarget(guide_act_view1)
                                    .setDismissText("「点击这里继续」")
                                    .setContentText("下载完成，点击图片进入课程")
                                    .singleUse("2")
                                    .setDelay(0)
                                    .setListener(object : IShowcaseListener {
                                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {

                                        }

                                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                                            MaterialShowcaseView.resetSingleUse(this@GuideActivity, "3")
                                            MaterialShowcaseView.Builder(this@GuideActivity)
                                                    .setTarget(guide_act_view2)
                                                    .setDismissText("「点击这里继续」")
                                                    .setTitleText("按科目和领域找课程")
                                                    .setContentText("点击科目和领域可以筛选课程")
                                                    .singleUse("3")
                                                    .setDelay(0)
                                                    .setListener(object : IShowcaseListener {
                                                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {

                                                        }

                                                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                                                            this@GuideActivity.finish()
                                                        }
                                                    })
                                                    .show()
                                        }

                                    })
                                    .show()
                        }
                    })
                    .show()
        }
        guide_act_view4.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(this, "4")
            MaterialShowcaseView.Builder(this)
                    .setTarget(guide_act_view4)
                    .setDismissText("「点击这里继续」")
                    .setContentText("按页面左侧的教案来备课")
                    .setTitleText("我们帮助你丰富你的课堂")
                    .setDismissStyle(Typeface.SANS_SERIF)
                    .singleUse("4")
                    .setDelay(0)
                    .setListener(object : IShowcaseListener {
                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {

                        }

                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                            MaterialShowcaseView.resetSingleUse(this@GuideActivity, "5")
                            MaterialShowcaseView.Builder(this@GuideActivity)
                                    .setTarget(guide_act_view5)
                                    .setDismissText("「点击这里继续」")
                                    .setContentText("课堂中使用的素材在右侧")
                                    .setTitleText("我们帮助你丰富你的课堂")
                                    .setDismissStyle(Typeface.SANS_SERIF)
                                    .singleUse("5")
                                    .setDelay(0)
                                    .setListener(object : IShowcaseListener {
                                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {
                                        }

                                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                                            MaterialShowcaseView.resetSingleUse(this@GuideActivity, "6")
                                            MaterialShowcaseView.Builder(this@GuideActivity)
                                                    .setTarget(guide_act_view3)
                                                    .setDismissText("「点击这里继续」")
                                                    .setContentText("点击小心心收藏课程")
                                                    .setTitleText("这课很好先记下来")
                                                    .setDismissStyle(Typeface.SANS_SERIF)
                                                    .singleUse("6")
                                                    .setDelay(0)
                                                    .setListener(object : IShowcaseListener {
                                                        override fun onShowcaseDisplayed(p0: MaterialShowcaseView?) {

                                                        }

                                                        override fun onShowcaseDismissed(p0: MaterialShowcaseView?) {
                                                            this@GuideActivity.finish()
                                                        }
                                                    })
                                                    .show()
                                        }
                                    })
                                    .show()
                        }
                    })
                    .show()
        }
        val activityName = intent.getStringExtra("ACTIVITY_NAME")
        when (activityName) {
            "LESSON" -> {
                guide_act_constraintLayout.setBackgroundResource(R.drawable.guide_image3)
                setSimulateClick(guide_act_view4, guide_act_view4.width / 2 - guide_act_view4.x, guide_act_view4.height / 2 - guide_act_view4.y)
            }
            "INDEX" -> {
                guide_act_constraintLayout.setBackgroundResource(R.drawable.guide_image1)
                setSimulateClick(guide_act_view1, guide_act_view1.width / 2 - guide_act_view1.x, guide_act_view1.height / 2 - guide_act_view1.y)
            }
        }


    }

    private fun setSimulateClick(view: View, x: Float, y: Float) {
        val downTime = SystemClock.uptimeMillis()
        val downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0)
//        downTime += 2500
        val upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x, y, 0)
//        view.onTouchEvent(downEvent)
//        view.onTouchEvent(upEvent)
        view.dispatchTouchEvent(downEvent)
        view.dispatchTouchEvent(upEvent)
        downEvent.recycle()
        upEvent.recycle()
    }

    override fun resume() {

    }

    companion object {
        public enum class ActivityName {
            Lesson,
            Index
        }

        fun newIntent(packageContext: Context, activityName: ActivityName): Intent {
            val intent = Intent(packageContext, GuideActivity::class.java)
            when (activityName) {
                ActivityName.Lesson -> {
                    intent.putExtra("ACTIVITY_NAME", "LESSON")
                }
                ActivityName.Index -> {
                    intent.putExtra("ACTIVITY_NAME", "INDEX")
                }
            }
            return intent
        }
    }
}
