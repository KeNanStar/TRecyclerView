package com.robot.recycle.common;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午6:59
 */
public class TaskExecutor {
    private static Handler mMainH;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static int KEEP_ALIVE_TIME = 6;
    private static volatile ThreadPoolExecutor sExecutor;
    private static BlockingQueue<Runnable> sQueue = new LinkedBlockingQueue();


    private static ThreadPoolExecutor getExecutor() {
        if (sExecutor == null) {
            Class var0 = ThreadFactory.class;
            synchronized(ThreadFactory.class) {
                sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, (long)KEEP_ALIVE_TIME, TimeUnit.SECONDS, sQueue);
            }
        }

        return sExecutor;
    }


    private static Handler getMainHandler() {
        if (mMainH == null) {
            mMainH = new Handler(Looper.getMainLooper());
        }

        return mMainH;
    }

    public static void runInUiThread(Runnable runnable){
        getMainHandler().post(runnable);
    }


    public static void runDelayedTimeInUiThread(Runnable runnable, long delayedTime){
        getMainHandler().postDelayed(runnable, delayedTime);
    }


    public static void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }


}
