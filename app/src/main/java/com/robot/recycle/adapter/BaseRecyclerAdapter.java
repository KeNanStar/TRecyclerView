package com.robot.recycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.robot.recycle.entity.BaseViewHolder;

import java.util.ArrayList;

/**
 * @author xing.hu
 * @since 2018/7/20, 下午4:44
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;
    protected ArrayList<T> mDatas = new ArrayList<>();


    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(ArrayList<T> data){
        if(data != null) {
            mDatas.clear();
            mDatas.addAll(data);
            notifyDataSetChanged();
        }

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return createHolder(parent, viewType, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindData((BaseViewHolder) holder, position);
    }


    protected T getItem(int position){
        if(mDatas != null && (position >= 0 && position < mDatas.size())){
            return mDatas.get(position);
        }else{
            return  null;
        }
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected  abstract BaseViewHolder createHolder(ViewGroup parent, int viewType, Context context);

    protected abstract void bindData(BaseViewHolder holder, int position);

     public  ArrayList<T> getData(){
         return  mDatas;
     }


}
