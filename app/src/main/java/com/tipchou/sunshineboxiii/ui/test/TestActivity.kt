package com.tipchou.sunshineboxiii.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

class TestActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_test

    @SuppressLint("SetTextI18n")
    override fun created(bundle: Bundle?) {
        test_act_button.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(this, "1")
            MaterialShowcaseView.Builder(this)
                    .setTarget(test_act_button)
                    .setDismissText("确定")
                    .setContentText("点击科目和领域可以筛选课程")
                    .setTitleText("按科目和领域查找课程")
                    .singleUse("1")
                    .setDelay(0)
                    .show()
        }


    }


    override fun resume() {
    }


}
