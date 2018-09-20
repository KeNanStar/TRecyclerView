package com.robot.recycler.entity;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author xing.hu
 * @since 2018/7/20, 下午4:28
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private Object mData;

    public BaseViewHolder(View view) {
        super(view);
        this.mViews = new SparseArray<>();
    }

    public <M> M getData(){
        return (M)mData;
    }

    public <M> void setData(M data){
       this.mData = data;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }
}
