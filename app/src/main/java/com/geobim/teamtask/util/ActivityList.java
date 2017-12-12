package com.geobim.teamtask.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃永无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Activity管理类
 */
public class ActivityList {
    private static List<Activity> activitys = new ArrayList<>();
    private static ActivityList instance;

    private ActivityList() {
        activitys = new LinkedList();
    }

    public static ActivityList getInstance() {
        if (null == instance) {
            instance = new ActivityList();
        }
        return instance;

    }

    public int getActivitySize() {
        return activitys.size();
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }

    }

    public void removeActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            activitys.remove(activity);
        }
    }


    // 遍历所有Activity并finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
    }

}
