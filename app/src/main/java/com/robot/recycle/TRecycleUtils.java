package com.robot.recycle;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:58
 */
public class TRecycleUtils {

    public static int dip2px(Context context, int dip) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (dip * densityDpi) / 160;
    }

    public static int px2dip(Context context, int px) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (px * 160) / densityDpi;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics sDisplay = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowManager != null){
            windowManager.getDefaultDisplay().getMetrics(sDisplay);
            return sDisplay.widthPixels;
        }
        return  -1;

    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics sDisplay = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowManager != null){
            windowManager.getDefaultDisplay().getMetrics(sDisplay);
            return sDisplay.heightPixels;
        }
        return  -1;
    }


}
