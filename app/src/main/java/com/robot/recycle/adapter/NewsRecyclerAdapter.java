package com.robot.recycle.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robot.recycle.entity.BaseViewHolder;

import java.util.HashMap;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午5:11
 */
public class NewsRecyclerAdapter extends BaseRecyclerAdapter<String> {
    private static final  int FOOTER_COUNT = 1;//底部View个数
    private static final int ITEM_TYPE_FOOTER = -2;   //底部刷新view类型
    private static final int ITEM_TYPE_CONTENT = -3;   //底部刷新view类型
    private Context mCtx;
    //private TextView mTextView =



    public NewsRecyclerAdapter(Context context) {
        super(context);
        init(context);
    }

    private void init(Context ctx){
        mCtx = ctx;
    }



    @Override
    protected BaseViewHolder createHolder(ViewGroup parent, int viewType, Context context) {
        return buildHolder(parent, viewType, context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        if (isFooter(position)) {
            return;
        }
        initData(holder, position);
    }

    private BaseViewHolder buildHolder(ViewGroup parent, int viewType, Context context) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case ITEM_TYPE_FOOTER:
                //holder = new BaseViewHolder(mFooterHolder.getFooterView());
                holder = new BaseViewHolder(new TextView(mCtx));
                break;
            default:
                //ITEM_TYPE_CONTENT
                View convertView = new TextView(mCtx);
                holder = new BaseViewHolder(convertView);
                final BaseViewHolder finalHolder = holder;
                break;
        }
        return holder;
    }

    private void initData(BaseViewHolder holder, final int position) {
        final int type = getItemViewType(position);
        //BaseIntimeEntity entity = (BaseIntimeEntity) getItem(position);
        //holder.setData(entity);
        if(holder.itemView instanceof  TextView){
            ((TextView)holder.itemView).setText("pos:" + position);

        }

    }


    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) {
            //底部View
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_CONTENT;

        }
    }

   /* public void setFooterHolder(RecyclerFooterHolder mFooterHolder) {
        this.mFooterHolder = mFooterHolder;
    }*/

    /**
     * 判断当前item是否是FooterView
     */
    public boolean isFooter(int position) {
        return position >= (getItemCount() - FOOTER_COUNT);
    }
}
