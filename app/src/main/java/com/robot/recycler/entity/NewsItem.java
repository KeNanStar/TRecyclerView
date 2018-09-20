package com.robot.recycler.entity;

import com.robot.recycler.TRecyclerUtils;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午8:42
 */
public class NewsItem {
    public static final String[] TITLES = {"互联网金融", "云计算", "机器学习", "人工智能"};
    public static final String[] CONTENTS = {"互联网金融不是互联网和金融业的简单结合，而是在实现安全、移动等网络技术水平上，被用户熟悉接受后（尤其是对电子商务的接受），自然而然为适应新的需求而产生的新模式及新业务",
            "云计算（cloudcomputing）是基于互联网的相关服务的增加、使用和交付模式，通常涉及通过互联网来提供动态易扩展且经常是虚拟化的资源。"
            , "机器学习(Machine Learning, ML)是一门多领域交叉学科，涉及概率论、统计学、逼近论、凸分析、算法复杂度理论等多门学科。"
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
