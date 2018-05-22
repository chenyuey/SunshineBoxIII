package com.tipchou.sunshineboxiii.support;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 由邵励治于2017/11/17创造.
 */

public class ActivityCollectorUtils {

    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}