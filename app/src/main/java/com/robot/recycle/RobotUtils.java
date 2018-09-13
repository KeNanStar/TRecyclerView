package com.robot.recycle;

import android.content.Context;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:58
 */
public class RobotUtils {

    public static int dip2px(Context context, int dip) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (dip * densityDpi) / 160;
    }

    public static int px2dip(Context context, int px) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (px * 160) / densityDpi;
    }
}
