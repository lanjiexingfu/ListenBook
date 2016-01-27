package com.lib.base.app;

import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager      instance;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 获取当前Activity的上一个Activity
     */
    public void findPrevActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0) if (activityStack.peek().getClass() == cls) {
            break;
        } else {
            finishActivity(activityStack.peek());
        }
    }

    /**
     * 退出应用程序
     *
     * @param context       上下文
     * @param hasBackground 是否开开启后台运行
     */
    public void exitApp(Context context, Boolean hasBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());

        } catch (Exception e) {

        } finally {
            /** 只能杀死普通后台 **/
            /** 信鸽后台进程，无法通过这个方法杀死。它自身有一个监控后台无法通过次方法杀死，而它的另一个负责推送的后台在杀死后会被监控后台重新拉起 **/
            if (hasBackground) {
                killBackgroundProcess(context);
            }
            /** 退出当前进程 **/
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 杀死后台进程
     *
     * @param context
     */
    public static void killBackgroundProcess(Context context) {
        //获取一个ActivityManager 对象
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取系统中所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        //获取当前activity所在的进程
        int currentUid = context.getApplicationInfo().uid;
        String currentProcess = context.getApplicationInfo().processName;
        //对系统中所有正在运行的进程进行迭代，如果进程名不是当前进程，则Kill掉
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            String processName = appProcessInfo.processName;
            DLog.e("zcl", "RunningAppProcessInfo -->UID:" + appProcessInfo.uid + "PID:" + appProcessInfo.pid + "--ProcessName:" + processName);
            if (processName.contains(currentProcess) && !processName.equals(currentProcess)) {
                activityManager.killBackgroundProcesses(processName);
                DLog.e("zcl", "Killed!!!!!!!");
            }
        }
    }
}