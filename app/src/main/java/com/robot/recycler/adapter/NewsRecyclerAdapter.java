package com.robot.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robot.recycler.R;
import com.robot.recycler.TRecyclerUtils;
import com.robot.recycler.entity.BaseViewHolder;
import com.robot.recycler.entity.NewsItem;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午8:42
 */
public class NewsRecyclerAdapter extends BaseRecyclerAdapter<NewsItem> {

    private Context mCtx;

    private static final int NEWS_ITEM_TYPE_PIC = 1;
    private static final int NEWS_ITEM_TYPE_NORMAL = 2;


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
            case NEWS_ITEM_TYPE_PIC:
                View itemView = LayoutInflater.from(context).inflate(
                        R.layout.item_pic_layout, parent, false);
                holder = new BaseViewHolder(itemView);
                break;
            case NEWS_ITEM_TYPE_NORMAL:
                View normalItemView = LayoutInflater.from(context).inflate(
                        R.layout.item_normal_layout, parent, false);
                holder = new BaseViewHolder(normalItemView);
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
            case NEWS_ITEM_TYPE_PIC:
                if (item != null) {
                    ((TextView) holder.getView(R.id.title)).setText(item.mTitle);
                    ((TextView) holder.getView(R.id.content)).setText(item.mContent);
                    ((ImageView) holder.getView(R.id.img)).setImageResource(item.mResId);
                }
                break;
            case NEWS_ITEM_TYPE_NORMAL:
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
        int itemType = position % 2;
        if(itemType == 0){
            return NEWS_ITEM_TYPE_NORMAL;
        }else{
            return NEWS_ITEM_TYPE_PIC;
        }
    }


}
