package com.robot.recycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robot.recycle.R;
import com.robot.recycle.entity.BaseViewHolder;
import com.robot.recycle.entity.NewsItem;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午8:42
 */
public class NewsRecyclerAdapter extends BaseRecyclerAdapter<NewsItem> {

    private Context mCtx;

    private static final int NEWS_ITEM_TYPE = 1;


    public NewsRecyclerAdapter(Context context) {
        super(context);
        init(context);
    }

    private void init(Context ctx) {
        mCtx = ctx;
    }


    @Override
    protected BaseViewHolder createHolder(ViewGroup parent, int viewType, Context context) {
        return buildHolder(parent, viewType, context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        initData(holder, position);
    }

    private BaseViewHolder buildHolder(ViewGroup parent, int viewType, Context context) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case NEWS_ITEM_TYPE:
                View itemView = LayoutInflater.from(context).inflate(
                        R.layout.item_layout, parent, false);
                holder = new BaseViewHolder(itemView);
                break;
            default:
                break;
        }
        return holder;
    }

    private void initData(BaseViewHolder holder, final int position) {
        final int type = getItemViewType(position);
        NewsItem item = getItem(position);
        switch (type) {
            case NEWS_ITEM_TYPE:
                if (item != null) {
                    ((TextView) holder.getView(R.id.title)).setText(item.mTitle);
                    ((TextView) holder.getView(R.id.content)).setText(item.mContent);
                }
                break;
            default:
                break;

        }
    }


    @Override
    public int getItemViewType(int position) {
        return NEWS_ITEM_TYPE;
    }


}
