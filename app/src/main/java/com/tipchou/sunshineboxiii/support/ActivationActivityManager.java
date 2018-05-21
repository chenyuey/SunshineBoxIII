package com.tipchou.sunshineboxiii.support;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tipchou.sunshineboxiii.ui.base.BaseActivity;


/**
 * 由邵励治于2017/12/7创造.
 */

public abstract class ActivationActivityManager extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtils.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
