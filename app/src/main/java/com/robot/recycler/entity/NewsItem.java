package com.robot.recycler.entity;

import com.robot.recycler.TRecyclerUtils;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午8:42
 */
public class NewsItem {
    public static final String[] TITLES = {"欧冠C罗高难凌空斩神球", "任正非最新讲话透露了华为的“野心”和“心机”", "歌手吴亦凡？其实他是个演员", "人工智能:下一次技术革命！！！"};
    public static final String[] CONTENTS = {"尤文输了，但C罗的表现很出色。虽然主场1-2输给曼联令尤文图斯球迷感到郁闷，但是他们阵中当家前锋C罗却表现很精彩，他攻入了一粒非常漂亮的进球。",
            "前两天，华为心声论坛公布了任正非十月份在上研所5G业务汇报会上的讲话，其中，任总重点提到，要实现5G网络极简化，把复杂留给自己，简单留给客户——“先从5G SA组网做起，要做到网络架构极简、交易架构极简、网络极安全、隐私保护极可靠、能耗极低，全面实现领先”。"
            , "不要再纠缠音乐上粉丝刷榜的那点破事，吴亦凡其实是个演员。"
            , "人工智能（Artificial Intelligence），英文缩写为AI。它是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。"};
    public static final int[] IMG_IDS = {com.robot.recycler.R.mipmap.pic_1, com.robot.recycler.R.mipmap.pic_2, com.robot.recycler.R.mipmap.pic_3, com.robot.recycler.R.mipmap.pic_4};

    public String mTitle;
    public String mContent;
    public int mResId;

    public NewsItem() {
        int index = TRecyclerUtils.getRandomNum(4);
        mTitle = TITLES[index];
        mContent = CONTENTS[index];
        mResId = IMG_IDS[index];
    }

}
