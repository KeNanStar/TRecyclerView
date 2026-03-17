package com.robot.recycler.listener;

/**
 * @author xing.hu
 * @since 2026/3/10, 下午6:21
 */
public interface IPullRefresh {
    void pullRefresh();
    void pullRefreshEnable(boolean enable);
    void pullRefreshEnd();

}
